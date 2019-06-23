package uilt;

public class Constants {
    public static final ProPertyUtil property = new ProPertyUtil("compareTest.properties");

    public String getAppPath() {
        return property.get("APP_PATH");
    }

    public int getCompareTest_Num() {
        return Integer.parseInt(property.get("CompareTest_Num"));
    }



}
