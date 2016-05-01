package com.imba.imbalibrary;

import com.imba.exception.AppException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by zace on 2015/5/1.
 */
public class UploadUtil {


    public static void upload(OutputStream out, String filePath, OnProgressUploadListener listener) throws AppException {
        String BOUNDARY = "7d4a6d158c9";
        DataOutputStream outputStream = new DataOutputStream(out);
        try {
            outputStream.writeBytes("--" + BOUNDARY + "\r\n");
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file0\";filename=" + "\""
                    + filePath.substring(filePath.lastIndexOf("/") + 1) + "\"" + "\r\n");
            outputStream.writeBytes("\r\n");
            byte[] buffer = new byte[1024];

            FileInputStream inputStream = new FileInputStream(filePath);
            long total = new File(filePath).length();
            long currentSize = 0;
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, buffer.length);

                if (listener != null) {
                    listener.onUpload(currentSize += len, total, currentSize / total);
                }
            }

            inputStream.close();
            outputStream.writeBytes("\r\n");
            byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();
            outputStream.write(end_data);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void upload(OutputStream out, String postContent, ArrayList<FileEntity> fileEntities, OnProgressUploadListener listener) throws AppException {

        String BOUNDARY = "7d4a6d158c9";
        String PREFIX = "--", LINEND = "\r\n";
        String CHARSET = "UTF-8";
        try {
            DataOutputStream outputStream = new DataOutputStream(out);
            StringBuilder sb = new StringBuilder();
            sb.append(BOUNDARY);
            sb.append(PREFIX);
            sb.append(LINEND);

            sb.append("Content-Disposition: form-data;name=\"+data\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);

            sb.append(postContent);
            sb.append(LINEND);

            outputStream.write(sb.toString().getBytes());

            long total = 0;
            if (listener != null) {
                for (FileEntity fileEntity : fileEntities) {
                    if (fileEntity.getFileLength() > 0) {
                        total += fileEntity.getFileLength();
                    }
                }
            }

            int i = 0;
            long currentSize = 0;
            for (FileEntity fileEntity : fileEntities) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(BOUNDARY);
                sb1.append(PREFIX);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"file" + (i++) + "\"; filename=\"" + fileEntity.getName() + "\"" + LINEND);
                sb1.append("Content-Type: " + fileEntity.getType() + LINEND);
                sb1.append(LINEND);
                outputStream.write(sb1.toString().getBytes());

                InputStream is = new FileInputStream(fileEntity.getPath());
                byte[] buffer = new byte[1024];

                int len;
                while ((len = is.read(buffer)) != -1) {

                    if (listener != null) {
                        listener.onUpload(currentSize += len, total, currentSize / total);
                    }

                    outputStream.write(buffer, 0, buffer.length);
                }

                is.close();
                outputStream.write(LINEND.getBytes());

            }
            byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();
            outputStream.write(end_data);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
