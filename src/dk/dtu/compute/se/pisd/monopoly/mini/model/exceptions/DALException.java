package dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions;


/**
 * Method for handling DAL exception
 */
public class DALException extends Exception {
    //Til Java serialisering...
    private static final long serialVersionUID = 7355418246336739229L;

    public DALException(String msg, Throwable e) {
        super(msg,e);
    }

    public DALException(String msg) {
        super(msg);
    }

}
