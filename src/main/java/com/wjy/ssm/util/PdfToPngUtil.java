package com.wjy.ssm.util;

//import org.apache.pdfbox.pdfparser.NonSequentialPDFParser;
//import org.apache.pdfbox.pdfparser.PDFParser;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2017/10/19.
 */
public class PdfToPngUtil {
    /*public List<String> testPdf(String pdfName){
        String pdfPath=ReadProperties.readPropertiesFromfiles("/ftpConfig.properties", "localPath", "CONF_HOME")+pdfName,savePath = ReadProperties.readPropertiesFromfiles("/ftpConfig.properties", "localPath", "CONF_HOME"),imgType = "png";
        String fileName = pdfPath.substring(pdfPath.lastIndexOf("/")+1, pdfPath.length());
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        InputStream is = null;
        PDDocument pdDocument = null;
        List<String> pngList = new ArrayList<>();
        try {
            is = new BufferedInputStream(new FileInputStream(pdfPath));
            NonSequentialPDFParser parser = new NonSequentialPDFParser(is);
            parser.parse();
            pdDocument = parser.getPDDocument();

            List<PDPage> pages = pdDocument.getDocumentCatalog().getAllPages();
            for (int i = 0; i < pages.size(); i++) {
                String saveFileName = savePath+"/"+fileName+i+"."+imgType;
                PDPage page =  pages.get(i);
                pdfPage2Img(page,saveFileName,imgType);
                pngList.add(i,"/mdd-xapp-web/fileDownload/"+fileName+i+".png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(pdDocument != null){
                try {
                    pdDocument.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pngList;
    }

    public void pdfPage2Img(PDPage page,String saveFileName,String imgType) throws IOException{

        BufferedImage img_temp  = page.convertToImage();
        Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix(imgType);
        ImageWriter writer = (ImageWriter) it.next();
        ImageOutputStream imageout = ImageIO.createImageOutputStream(new FileOutputStream(saveFileName));
        writer.setOutput(imageout);
        writer.write(new IIOImage(img_temp, null, null));
        imageout.close();
    }*/

}
