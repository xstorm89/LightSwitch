package controller;

import gpio.GpioControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


/**
 * This is the controller used by Spring framework.
 * <p>
 * The basic function of this controller is to map
 * each HTTP API Path to the correspondent method.
 *
 */

@RestController
public class WebController {      

	boolean light_on =false;
	GpioControl pin_1 =new GpioControl(2,1); //light
	GpioControl pin_2 =new GpioControl(3,1);
	GpioControl pin_3 =new GpioControl(4,1);
	GpioControl pin_4 =new GpioControl(5,1);
	GpioControl pin_5 =new GpioControl(6,1);
	GpioControl pin_6 =new GpioControl(12,1);
	GpioControl pin_7 =new GpioControl(13,1);
	GpioControl pin_8 =new GpioControl(14,1);


    @RequestMapping(value = "/lights/toggle", method = RequestMethod.GET)
    Boolean toggleLight() {
    	
    	
    	light_on = !light_on;
    	Boolean response = new Gson().fromJson(Boolean.toString(light_on), Boolean.class);
    	
    	toggle(1);
    	System.out.println(light_on);
		return new Gson().fromJson(Boolean.toString(light_on), Boolean.class);

    }
    
    @RequestMapping(value = "/lights/status", method = RequestMethod.GET)
    Boolean statusLight() {

    	Boolean response = new Gson().fromJson(Boolean.toString(light_on), Boolean.class);

        return new Gson().fromJson(Boolean.toString(light_on), Boolean.class);

    }
    
    @RequestMapping(value = "/lights/test", method = RequestMethod.GET)
    
    Boolean lightTesting() throws InterruptedException{
    	
    	toggle(1);
		Thread.sleep(1);
    	toggle(2);
		Thread.sleep(1);
    	toggle(3);
		Thread.sleep(1);
    	toggle(4);
		Thread.sleep(1);
    	toggle(5);
		Thread.sleep(1);
    	toggle(6);
		Thread.sleep(1);
    	toggle(7);
		Thread.sleep(1);
    	toggle(8);
		Thread.sleep(1); 	
    	
    	return true;
    }
    
    
    private void toggle(int pinNum){
    	
    	switch (pinNum){
    	case 1:
    	if(pin_1.pinStatus())
    		pin_1.turnOffPin();
    	else
    		pin_1.turnOnPin();
    	break;
    	case 2:
    	if(pin_2.pinStatus())
    		pin_2.turnOffPin();
    	else
    		pin_2.turnOnPin();
    	break;
    	case 3:
    	if(pin_3.pinStatus())
    		pin_3.turnOffPin();
    	else
    		pin_3.turnOnPin();
    	break;
    	case 4:
    	if(pin_4.pinStatus())
    		pin_4.turnOffPin();
    	else
    		pin_4.turnOnPin();
    	break;
    	case 5:
    	if(pin_5.pinStatus())
    		pin_5.turnOffPin();
    	else
    		pin_5.turnOnPin();
    	break;
    	case 6:
    	if(pin_6.pinStatus())
    		pin_6.turnOffPin();
    	else
    		pin_6.turnOnPin();
    	break;
    	case 7:
    	if(pin_7.pinStatus())
    		pin_7.turnOffPin();
    	else
    		pin_7.turnOnPin();
    	break;
    	case 8:
    	if(pin_8.pinStatus())
    		pin_8.turnOffPin();
    	else
    		pin_8.turnOnPin();
    	break;
		default:
			System.out.println("invalid pin Number");
			break;
    	
    	}
    	
    	

    	
    }

}