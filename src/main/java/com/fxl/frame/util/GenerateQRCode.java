package com.fxl.frame.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 基于google Zxing实现二维码生成
 * @Description TODO
 * @author fangxilin
 * @date 2017-5-23
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class GenerateQRCode {
	
	private static final String FILE_DIR = System.getProperty("java.io.tmpdir");
	
	/**
	 * 生成二位码
	 * @createTime 2017-5-31,下午3:08:44
	 * @createAuthor fangxilin
	 * @param content
	 * @return
	 */
	public static String generate(String content) {
		String targetFileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
		String filePath = FILE_DIR + targetFileName;
        int width = 300; // 图像宽度  
        int height = 300; // 图像高度  
        String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix = null;
		try {
			// 生成矩阵  
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			Path path = FileSystems.getDefault().getPath(filePath);
			//MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
		    MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像 
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return filePath;
	}
	
	/**
	 * 解码二维码
	 * @createTime 2017-5-31,下午3:08:36
	 * @createAuthor fangxilin
	 * @param filePath
	 * @return 二维码内容
	 */
	public static String decode(String filePath) {
		BufferedImage image = null;
		String QRCodeString = "";
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);
			QRCodeString = result.getText();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return QRCodeString;
	}
}
