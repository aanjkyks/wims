package analizers;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.hibernate.search.backend.lucene.analysis.model.dsl.LuceneAnalysisDefinitionContainerContext;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

public class MyLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
    @Override
    public void configure(LuceneAnalysisDefinitionContainerContext context) {
        context.analyzer( "myAnalyzer" ).custom() 
                .tokenizer( StandardTokenizerFactory.class ) 
                .tokenFilter( ASCIIFoldingFilterFactory.class ) 
                .tokenFilter( LowerCaseFilterFactory.class ) 
                .tokenFilter( SnowballPorterFilterFactory.class ) 
                        .param( "language", "English" ); 
    }
}