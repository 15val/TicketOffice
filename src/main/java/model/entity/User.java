package model.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private BigInteger balance;
    private String nickname;
    private String password;
    private String email;
    private String name;
    private ROLE role;
    public enum ROLE {
        USER, ADMIN
    }

    public static class Builder{
        private final User user;

        public Builder(){
            user = new User();
        }

        public Builder withId (int id){
            user.id = id;
            return this;
        }
        public Builder withBalance(BigInteger balance){
            user.balance = balance;
            return this;
        }
        public Builder withNickname (String nickname){
            user.nickname = nickname;
            return this;
        }
        public Builder withPassword (String password){
            user.password = password;
            return this;
        }
        public Builder withEmail (String email){
            user.email = email;
            return this;
        }
        public Builder withName (String name){
            user.name = name;
            return this;
        }
        public Builder withRole (ROLE role){
            user.role = role;
            return this;
        }

        public User build(){
            return user;
        }

    }
    public int getId() {
        return id;
    }
    public String getNickname() {
        return nickname;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public ROLE getRole() {
        return role;
    }
    public BigInteger getBalance() {return balance;}

}
/*
class Users{
    String name;
    int age;
    public Users(String name, int age){
         this.name = name;
         this.age = age;
    };



    public  static void main(String[] args){
        Users user1 =     new Users("Anna", 25);

        Users user2 =    new Users("Di", 15);

        Users user3 =   new Users("Nick", 12);

        Users user4 =   new Users("Rick", 20);

        Users user5 =    new Users("Lii", 18);
        List<Users> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);

        userList.stream().filter(users -> users.getAge() >= 18).forEach(users -> System.out.println(users.getName()));
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}*/



