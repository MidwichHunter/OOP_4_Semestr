import org.json.simple.JSONObject;

public class Hunter {
    private String name;
    private String rang;
    private int age;
    private String group;
    private String pdaNumber;

    public Hunter(String name, String rang, int age, String group, int pdaNumber) {
        this.name = name;
        this.rang = rang;
        this.age = age;
        this.group = group;
        this.pdaNumber = String.valueOf(pdaNumber);
    }

    // getters
    public String getName() {
        return name;
    }
    public String getRang() {
        return rang;
    }
    public int getAge() {
        return age;
    }
    public String getGroup() {
        return group;
    }
    public String getPdaNumber() {
        return pdaNumber;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setRang(String rang) {
        this.rang = rang;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setPdaNumber(String pdaNumber) {
        this.pdaNumber = pdaNumber;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("rang", rang);
        json.put("age", age);
        json.put("group", group);
        json.put("pda_number", pdaNumber);
        return json;
    }

    public static Hunter fromJSON(JSONObject json) {
        try {
            String name = (String) json.get("name");
            String rang = (String) json.get("rang");
            int age = ((Long) json.get("age")).intValue();
            String group = (String) json.get("group");
            int pdaNumber = (int) json.get("pda_number");

            return new Hunter(name, rang, age, group, pdaNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}