package com.jdr.interview.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import com.jdr.interview.utils.OssUtils.PutObjectProgressListener;

public class OssUtils {
	private static OSSClient client;
	public static OSSClient getOssClient() {
		if(client == null) {
			String endpoint = "http://oss-cn-beijing.aliyuncs.com";
			// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
			String accessKeyId = "LTAIrQdpsIaRNbg4";
			String accessKeySecret = "UUTn5SuVpNl52vtGNRH049R7wipMuR";
			// 创建ClientConfiguration实例
			ClientConfiguration conf = new ClientConfiguration();
			// 设置OSSClient使用的最大连接数，默认1024
			conf.setMaxConnections(200);
			// 设置请求超时时间，默认50秒
			conf.setSocketTimeout(10000);
			// 设置失败请求重试次数，默认3次
			conf.setMaxErrorRetry(5);
			client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
		}
		return client;
	}
	public static void shutDownClilent() {
		getOssClient().shutdown();
	}
	public static String putObject(String key,InputStream is) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
		try {
			PutObjectProgressListener putObjectProgressListener = new PutObjectProgressListener();
			getOssClient().putObject(new PutObjectRequest("jdr-interview",key+"/"+uuid,is)
					.withProgressListener(putObjectProgressListener));
			if(putObjectProgressListener.isSucceed())
				return "http://jdr-interview.oss-cn-beijing.aliyuncs.com/"+key+"/"+uuid;
			else
				return "";
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}	
	}
	static class PutObjectProgressListener implements ProgressListener {
	   
	    private boolean succeed = false;
	    @Override
	    public void progressChanged(ProgressEvent progressEvent) {
	        ProgressEventType eventType = progressEvent.getEventType();
	        switch (eventType) {
	        case TRANSFER_STARTED_EVENT:
	            System.out.println("Start to upload......");
	            break;
	        case TRANSFER_COMPLETED_EVENT:
	            this.succeed = true;
	            System.out.println("Succeed to upload, bytes have been transferred in total");
	            break;
	        case TRANSFER_FAILED_EVENT:
	            System.out.println("Failed to upload,  bytes have been transferred");
	            break;
			default:
	            break;
	        }
	    }
	    public boolean isSucceed() {
	        return succeed;
	    }
	}
}
