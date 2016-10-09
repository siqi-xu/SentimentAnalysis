package preprocess;

/**
 * ���ܣ���ϰʹ��CoreNLP�����Ӣ�Ĵ���
 * ʱ�䣺2016��4��22�� 14:03:42
 * 
*/
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
// import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class CoreNLP_pos_string {
    public static void main(String[] args) {
        /**
         * ����һ��StanfordCoreNLP object
         * tokenize(�ִ�)��ssplit(�Ͼ�)�� pos(���Ա�ע)��lemma(���λ�ԭ)��
         * ner(����ʵ��ʶ��)��parse(�﷨����)��ָ�����⣿ͬ��ʷֱ棿
         */
        
        Properties props = new Properties();    
        props.put("annotators", "tokenize, ssplit, pos");    // ����Annotators
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // ���δ���
        
        String text = "accept acceptance acclaim acclaimation";               // �����ı�
        
        Annotation document = new Annotation(text);    // ����text����һ���յ�Annotation
        pipeline.annotate(document);                   // ��textִ�����е�Annotators�����֣�
        
        // �����sentences �а��������з���������������ɻ�֪�����
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        System.out.println("word\t\tpos");
        
        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                
                String word = token.get(TextAnnotation.class);            // ��ȡ�ִ�
                String pos = token.get(PartOfSpeechAnnotation.class);     // ��ȡ���Ա�ע
               
                System.out.println(word+"\t"+ "\t" + pos);
            }
            
        }
        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
    }
}