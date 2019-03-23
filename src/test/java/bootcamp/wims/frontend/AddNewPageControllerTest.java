package bootcamp.wims.frontend;

import bootcamp.wims.auth.model.User;
import bootcamp.wims.auth.repository.UserRepository;
import bootcamp.wims.dbControl.DbManager;
import bootcamp.wims.model.NoteRepository;
import bootcamp.wims.model.Tag;
import bootcamp.wims.model.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = WimsTestConfiguration.class
)
@AutoConfigureMockMvc
public class AddNewPageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbManager dbManager;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TagRepository tagRepository;
    @MockBean
    private NoteRepository noteRepository;
    @Mock
    private Principal mockPrincipal;
    @Mock
    private User mockUser;
    @Mock
    private Tag mockTag;

    @Test
    @WithMockUser
    public void testAddController() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/add");
        when(userRepository.findByUsername("user")).thenReturn(mockUser);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Note date")));
    }

    @Test
    @WithMockUser
    public void testAddNewNote_savedValidNote() throws Exception {
        EditNoteModel model = new EditNoteModel(0, "2019-03-23", "foobar", "baz baz", "desc");
//        when(tagRepository.findByName("baz baz")).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/add_save").with(csrf())
//                .principal(mockPrincipal)
                .flashAttr("note", model);
        when(userRepository.findByUsername("user")).thenReturn(mockUser);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(302));
    }
}
