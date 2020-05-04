package nl.pse.site.seproject.rest.model;
public class ClientError {
    private String message;

    public ClientError(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
