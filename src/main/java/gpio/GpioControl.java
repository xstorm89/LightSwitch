package gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GpioControl {
	
	GpioController gpio;
	GpioPinDigitalOutput pin;

	public GpioControl(int pinNum, int high){
		//high=1 init set to high/ high=0 setto low
		 this.gpio = GpioFactory.getInstance();
		 if(high==1)
			 this.pin = gpio.provisionDigitalOutputPin(mapPin(pinNum), "MyLED", PinState.HIGH);
		 else
			 this.pin = gpio.provisionDigitalOutputPin(mapPin(pinNum), "MyLED", PinState.LOW);


	}
	
	public void turnOnPin( ){
		pin.high();
        System.out.println("--> GPIO state should be: ON");

	}
	public void turnOffPin(){
		pin.low();
        System.out.println("--> GPIO state should be: OFF");

	}
	
	public boolean pinStatus(){
		
		return pin.isHigh();
	}
	
	private Pin mapPin(int pinNum){
		
		Pin pin= RaspiPin.GPIO_00;
		switch (pinNum){
		case 0:
			break;
		case 1:
			pin =RaspiPin.GPIO_01;
			break;
		case 2:
			pin =RaspiPin.GPIO_02;
			break;
		case 3:
			pin =RaspiPin.GPIO_03;
			break;
		case 4:
			pin =RaspiPin.GPIO_04;
			break;
		case 5:
			pin =RaspiPin.GPIO_05;
			break;
		case 6:
			pin =RaspiPin.GPIO_06;
			break;
		case 7:
			pin =RaspiPin.GPIO_07;
			break;
		case 8:
			pin =RaspiPin.GPIO_08;
			break;
		case 9:
			pin =RaspiPin.GPIO_09;
			break;
		case 10:
			pin =RaspiPin.GPIO_10;
			break;
		case 11:
			pin =RaspiPin.GPIO_11;
			break;
		case 13:
			pin =RaspiPin.GPIO_13;
			break;
		case 14:
			pin =RaspiPin.GPIO_14;
			break;	
		case 15:
			pin =RaspiPin.GPIO_15;
			break;			
		case 16:
			pin =RaspiPin.GPIO_16;
			break;		
		case 17:
			pin =RaspiPin.GPIO_17;
			break;		
		case 18:
			pin =RaspiPin.GPIO_18;
			break;
		case 19:
			pin =RaspiPin.GPIO_19;
			break;			
		case 20:
			pin =RaspiPin.GPIO_20;
			break;
		case 21:
			pin =RaspiPin.GPIO_21;
			break;
		case 22:
			pin =RaspiPin.GPIO_22;
			break;
		case 23:
			pin =RaspiPin.GPIO_23;
			break;
		case 24:
			pin =RaspiPin.GPIO_24;
			break;
		case 25:
			pin =RaspiPin.GPIO_25;
			break;
		case 26:
			pin =RaspiPin.GPIO_26;
			break;
		case 27:
			pin =RaspiPin.GPIO_27;
			break;
		case 28:
			pin =RaspiPin.GPIO_28;
			break;
		case 29:
			pin =RaspiPin.GPIO_29;
			break;
		default:
			System.out.println("invalid pin Number");
			break;
		
		}
		
		return pin;
	}
	
	public void shutDown(){
		gpio.shutdown();
	}
	
}

