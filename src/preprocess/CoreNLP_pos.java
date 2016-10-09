package preprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class CoreNLP_pos {
	public void doProcess(String strDir) throws IOException {
		File fileDir = new File(strDir);
		if (!fileDir.exists()) {
			System.out.println("File not exist:" + strDir);
			return;
		}
		String subStrDir = strDir.substring(strDir.lastIndexOf("/")); //��ȡ�ļ�����
		String dirTarget = "D:/pos1" + subStrDir;
		if (!new File("D:/pos1").exists()) {
			new File("D:/pos1").mkdir();
		}
		File fileTarget = new File(dirTarget);
		if (!fileTarget.exists()) {
			fileTarget.mkdir();
		}
		File[] srcFiles = fileDir.listFiles();
		String[] stemFileNames = new String[srcFiles.length];
		for (int i = 0; i < srcFiles.length; i++) {
			String fileFullName = srcFiles[i].getCanonicalPath(); //get·��ȫ��
			String fileShortName = srcFiles[i].getName(); //get fileName���ļ�����
			if (!new File(fileFullName).isDirectory()) {
				System.out.println("Begin preprocess:" + fileFullName);
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(dirTarget + "/" + fileShortName);
				createProcessFile(fileFullName, stringBuilder.toString());
				stemFileNames[i] = stringBuilder.toString();
			} else {
				fileFullName = fileFullName.replace("\\", "/");
				doProcess(fileFullName);
			}
		}
	}

	private static void createProcessFile(String srcDir, String targetDir)
			throws IOException {
		// TODO Auto-generated method stub
		FileReader srcFileReader = new FileReader(srcDir);
		FileWriter targetFileWriter = new FileWriter(targetDir);
		BufferedReader srcFileBR = new BufferedReader(srcFileReader);

		String line;
		while ((line = srcFileBR.readLine()) != null) {
	        Properties props = new Properties();    
	        props.put("annotators", "tokenize, ssplit, pos");    
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // ���δ���
	        
	        Annotation document = new Annotation(line);    // ����text����һ���յ�Annotation
	        pipeline.annotate(document);                   // ��textִ��Annotators
	        
	        // �����sentences �а��������з���������������ɻ�֪�����
	        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	        System.out.println("word\t\tpos");
	        for(CoreMap sentence: sentences) {
	            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	            	String word = token.get(TextAnnotation.class);            // ��ȡ�ִ�
	            	String pos = token.get(PartOfSpeechAnnotation.class);     // ��ȡ���Ա�ע           
	                System.out.println(word+"\t"+ "\t" + pos);
	                targetFileWriter.append(word+"\t"+ "\t" + pos + "\n");
	            }
	        }
	        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
		}
		targetFileWriter.flush();
		targetFileWriter.close();
		srcFileReader.close();
		srcFileBR.close();
	}

	public  static void main(String []args) throws IOException {
		// TODO Auto-generated method stub
		CoreNLP_pos dataPrePro = new CoreNLP_pos();
		dataPrePro.doProcess("D:/level");
	}

}
