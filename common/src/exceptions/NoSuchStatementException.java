package exceptions;

public class NoSuchStatementException extends Exception{
    public NoSuchStatementException(){
        super("Не найдено подходящей записи");
    }
}
