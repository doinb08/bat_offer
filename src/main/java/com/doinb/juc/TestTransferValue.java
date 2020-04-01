package com.doinb.juc;

class Person{
    private Integer id;

    private String personName;

    public Person() {
    }

    public Person(Integer id, String personName) {
        this.id = id;
        this.personName = personName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}

/**
 * @author doinb
 *  笔试题 易错点
 */
public class TestTransferValue {
    private void changeValueInt(int age){
        age = 30;
    }

    private void changeValuePerson(Person person){
        person.setPersonName("doinb");
    }

    private void changeValueStr(String str){
        str = "doinb";  // 此处等于new String("doinb")
    }

    public static void main(String[] args) {
        TestTransferValue testTransferValue = new TestTransferValue();
        int age = 20;
        testTransferValue.changeValueInt(age);
        System.out.println("age----"+ age);

        Person person = new Person(18,"adc");
        testTransferValue.changeValuePerson(person);
        System.out.println("personName----"+person.getPersonName());

        String str = "abc";
        testTransferValue.changeValueStr(str);
        System.out.println("string---"+str);
    }

}
