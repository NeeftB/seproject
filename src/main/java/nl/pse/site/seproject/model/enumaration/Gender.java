package nl.pse.site.seproject.model.enumaration;

public enum  Gender {
    MEN ("Men",0),
    FEMALE ("Female",1)
    ;

    private final String gender;
    private final int gendercode;

    private Gender(String gender, int gendercode){
        this.gender = gender;
        this.gendercode = gendercode;
    }

    public int getGendercode(){
        return this.gendercode;
    }

}
