package org.vgr.ioc.core;

public class AopCar implements AopIVehicle{
	private String name;

	public AopCar(String string) {
		this.name = string;
	}

	public void start() {
		System.out.println("Car " + this.name + "  Started");
	}
	public void stop() {
	System.out.println("Car " + this.name + "  Stopped");

	}
	public void forward() {
	System.out.println("Car " + this.name + "  forwading");

	}

	public void reverse() {
		System.out.println("Car " + this.name + "  Reversing");

	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
