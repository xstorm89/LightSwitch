package gpio;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.RaspiPin;

public class MyLcd {
	
    private int LCD_ROWS = 2;
    private int LCD_ROW_1 = 0;
    private int LCD_ROW_2 = 1;
    private int LCD_COLUMNS = 16;
    private int LCD_BITS = 4;
	
    private GpioLcdDisplay lcd;
    
    public MyLcd(){
    	this.lcd =new GpioLcdDisplay(LCD_ROWS,          // number of row supported by LCD
                LCD_COLUMNS,       // number of columns supported by LCD
                RaspiPin.GPIO_28,  // LCD RS pin
                RaspiPin.GPIO_29,  // LCD strobe pin
                RaspiPin.GPIO_21,  // LCD data bit 1
                RaspiPin.GPIO_22,  // LCD data bit 2
                RaspiPin.GPIO_23,  // LCD data bit 3
                RaspiPin.GPIO_24); // LCD data bit 4 
    }
    
    public void setText(String text){
    	
    	clear();
    	lcd.write(text);

    }
    
    public void clear(){
    	lcd.clear();
    }

}
