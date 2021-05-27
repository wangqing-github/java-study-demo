package wq.study.demo.entity;


import wq.study.demo.utils.Builder;

//@Data
public class GirlFriend {
    private String name;
    private int age;
    private String phone;
    private String address;
    private String gift;
    public static GirlFriend get(){
        return Builder.of(GirlFriend::new)
                .with(GirlFriend::setName,"小芳")
                .with(GirlFriend::setAge,18)
                .with(GirlFriend::setAddress,"成都")
                .with(GirlFriend::setGift,"辣条")
                .with(GirlFriend::setPhone,"12345").build();
    }
    public static void main(String[] args) {
        GirlFriend girlFriend = Builder.of(GirlFriend::new)
                .with(GirlFriend::setName,"小芳")
                .with(GirlFriend::setAge,18)
                .with(GirlFriend::setAddress,"成都")
                .with(GirlFriend::setGift,"辣条")
                .with(GirlFriend::setPhone,"12345").build();
        System.out.println(girlFriend.getName());
        System.out.println(girlFriend.getAge());
        System.out.println(girlFriend.getPhone());
        System.out.println(girlFriend.getAddress());
        System.out.println(girlFriend.getGift());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
