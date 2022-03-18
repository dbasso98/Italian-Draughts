package dssc.exam.draughts.exceptions;

public class DraughtsException extends Exception {
    public DraughtsException(String message) {
        super(message);
    }
    public void printInformativeMessage(String addedInformation){
        System.out.println(addedInformation + this.getMessage());
    }

    public void printMessage(){
        System.out.println(this.getMessage());
    }

}
