package com.ilri.crop.Model;

public class Constants {
    public static final String  FERTILIZER="""
                            {
                    Urea : 0,
                    N : 0,
                    P2O5 : 0,
                    NPS : 0
                    }
                    """;
    public static final String  QUALITY=""" 
                    {
                    protein: 0,
                    WetGluten: 0
                    }
                     """;
    public static final String  SPACING="""
                    {
                    bRows : 0,
                    bPlants : 0
                    }
                    """;
    public static final String  YIELD="""
                    {
                    researchField : 0,
                    farmersField : 0
                    }
                    """;
    public static final  String BRACE = """
                   {
                   min : 0 ,
                   max : 0
                   }
                   """;
    public static final String  ADAPTATION="""
                    {
                    location:value,
                    altitude:{
                    min: 0,
                    max: 0
                    },
                    rainFall:{
                    min: 0,
                    max: 0
                    },
                    soilType:value
                    }
                    """;

    private Constants() {
    }
}
