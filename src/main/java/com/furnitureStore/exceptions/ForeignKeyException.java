package com.furnitureStore.exceptions;

public class ForeignKeyException extends RuntimeException{
	public ForeignKeyException() {
		    super("The given entity has a FOREIGN KEY constraint with an another entity. Delete the PARENT entity first BEFORE deleting the CHILD entity!!!");
	}
}