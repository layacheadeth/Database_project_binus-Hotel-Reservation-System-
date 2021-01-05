package sample.database;

public class User {
    private String name;
    private String gender;
    private String tel;
    private String email;
    private String password;
    private String cpassoword;
    private int id;

    private User user;


    public User(String name,String gender,String tel,String email,String password,String cpassword){
        this.name=name;
        this.gender=gender;
        this.tel=tel;
        this.email=email;
        this.password=password;
        this.cpassoword=cpassword;
    }

    public User(int id,String name,String gender,String tel,String email,String password,String cpassword){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.tel=tel;
        this.email=email;
        this.password=password;
        this.cpassoword=cpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassoword() {
        return cpassoword;
    }

    public void setCpassoword(String cpassoword) {
        this.cpassoword = cpassoword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


