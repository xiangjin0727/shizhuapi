package com.hz.util.common.httpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientUtils {
	public static Log log = LogFactory.getLog(HttpClientUtils.class);

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static void main(String[] args) throws KeyManagementException,
			NoSuchAlgorithmException, ClientProtocolException, IOException {
		// sendMsgOfCert("https://10.25.32.13:8007", "hello world", "123456",
		// "kserver.jks", "123456", "tclient.jks");
		send("https://app.hzdjr.com/hzcfmobile/mobiledata", 
				"{\"Data\":\"Abl4++Dsbwtu\\/z7KHBj89+wJdL3gsKvThg8i2eGt5MGh9I+wsZI23l6toHDwN4iPeHdTd7GB2Kx8L5ll60+khh9p5dzuggaT5VdY4BaGsJ5XfIw2k+KdNitGx\\/byww754g0wWQx\\/rRdG8YaW7quqG4aRQgF0UdCjgvHu7kX8eeWXkFvwZQw5rQ==\",\"UserToken\":\"e47c28b5dfd3fbb0d81c315089765610\",\"ServiceId\":\"S0024\"}");
	}

	public static String sendMsgOfCert(String urlString, String requestData,
			String CLIENT_CERT_PWD, String CLIENT_CERT_PATH,
			String TRUST_CERT_PWD, String TRUST_CERT_PATH) {
		StringBuffer sb = null;
		try {
			log.info("开始初始化https客户端！");
			SSLContext sslContext = SSLContext.getInstance("SSL");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance("SunX509");
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(ClassLoader.getSystemResourceAsStream(CLIENT_CERT_PATH),
					CLIENT_CERT_PWD.toCharArray());
			kmf.init(ks, CLIENT_CERT_PWD.toCharArray());
			KeyStore tks = KeyStore.getInstance("JKS");
			tks.load(ClassLoader.getSystemResourceAsStream(TRUST_CERT_PATH),
					TRUST_CERT_PWD.toCharArray());
			tmf.init(tks);
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			HostnameVerifier hostnameVerifier = new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
			// URL url = new URL("https://172.40.1.83:8007");
			URL url = new URL(urlString);
			HttpsURLConnection urlCon = (HttpsURLConnection) url
					.openConnection();
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-type",
					"text/xml;charset=GB18030");
			urlCon.setSSLSocketFactory(sslContext.getSocketFactory());
			OutputStream os = urlCon.getOutputStream();
			InputStream fis = new ByteArrayInputStream(
					requestData.getBytes("GB18030"));
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] bytes = new byte[1024];
			int len = -1;
			while ((len = bis.read(bytes)) != -1) {
				os.write(bytes, 0, len);
			}
			os.flush();
			bis.close();
			fis.close();
			os.close();
			InputStream is = urlCon.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println("sb:" + sb);
			br.close();
			is.close();
			urlCon.disconnect();
		} catch (Exception e) {
			e.fillInStackTrace();
			log.info("客户端调用失败：" + e.getMessage());
			throw new RuntimeException("https调用失败！");
		}
		return null;
	}

	public static void send(String requsetString, String requestData)
			throws NoSuchAlgorithmException, KeyManagementException,
			ClientProtocolException, IOException {
		X509TrustManager trustManager = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// Now put the trust manager into an SSLContext.
		SSLContext sslcontext = SSLContext.getInstance("SSL");
		sslcontext.init(null, new TrustManager[] { trustManager }, null);
		SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
		sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getConnectionManager().getSchemeRegistry()
				.register(new Scheme("https", sf, 443));
		HttpPost httpPost = new HttpPost(requsetString);
		String result = "";
//		httpPost.setHeader("Authorization", "basic "
//				+ "dGNsb3VkYWRtaW46dGNsb3VkMTIz");
//		httpPost.setHeader("Content-type", "application/xml");
		StringEntity reqEntity;
		// 将请求参数封装成HttpEntity
		reqEntity = new StringEntity(requestData);
		BufferedHttpEntity bhe = new BufferedHttpEntity(reqEntity);
		httpPost.setEntity(bhe);
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity resEntity = response.getEntity();
		InputStreamReader reader = new InputStreamReader(resEntity.getContent());
		char[] buff = new char[1024];
		int length = 0;
		while ((length = reader.read(buff)) != -1) {
			result += new String(buff, 0, length);
		}
		httpclient.getConnectionManager().shutdown();
		System.out.println(">>>:" + result);
	}
}