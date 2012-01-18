package moer.social.util;
import java.io.*;
import java.util.*;

public class PropertiesToXml {

	private static final String ENCODING = "UTF-8";
	private static final String PREFIX = "";
	private static final String POSTFIX = "-properties";

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			throw new RuntimeException("프라퍼티 파일 혹은 파일들이 있는 디렉토리 경로를 입력해주세요.");
		}
		File base = new File(args[0]);

		convert(base);
	}

	private static void convert(File file) throws Exception {
		if(file.isFile() && !file.getName().endsWith(".properties")) {
			return;
		}

		if (file.getName().equals(".svn") || file.getName().equals("target")) {
			return;
		}


		if (!file.exists()) {
			throw new RuntimeException(file.getCanonicalPath() + "파일이 존재하지 않음");
		}


		if (file.isFile()) {
			printf("Processing : %s ", file.getCanonicalPath());
			convertPropertiesToXml(file);
			printf("  ... 처리 완료\n");
			return;
		}

		for (File child : file.listFiles()) {
			convert(child);
		}
	}

	private static void convertPropertiesToXml(File file) throws IOException,
			FileNotFoundException {
		Properties props = new Properties();
		props.load(new FileInputStream(file));
		FileOutputStream xmlOs = null;
		try {
			File xmlFile = getXmlFile(file);
			xmlOs = new FileOutputStream(xmlFile);
			props.storeToXML(xmlOs, file.getName(), ENCODING);

		} finally {
			closeQuietly(xmlOs);
		}
	}

	private static File getXmlFile(File file) {

		File filePath = file.getParentFile();

		String filename = file.getName().replaceAll("\\.properties$", "");

		File xmlFile = new File(filePath, PREFIX + filename + POSTFIX + ".xml");
		return xmlFile;
	}

	private static void closeQuietly(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (Exception ex) {
				// ignored;
			}
		}
	}

	public static void printf(String format, Object... args) {
		System.out.printf(format, args);
	}
}
