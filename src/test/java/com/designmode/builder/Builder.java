package com.designmode.builder;

import java.util.ArrayList;
import java.util.List;

import com.designmode.factory.MailSender;
import com.designmode.factory.Sender;
import com.designmode.factory.SmsSender;

public class Builder {
	
	private List<Sender> list = new ArrayList<Sender>();  
    
    public void produceMailSender(int count){  
        for(int i=0; i<count; i++){  
            list.add(new MailSender());  
        }  
    }  
      
    public void produceSmsSender(int count){  
        for(int i=0; i<count; i++){  
            list.add(new SmsSender());  
        }  
    }
}
