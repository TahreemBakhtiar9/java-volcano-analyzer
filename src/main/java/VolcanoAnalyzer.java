import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class VolcanoAnalyzer {
    private List<Volcano> volcanos;

    public void loadVolcanoes(Optional<String> pathOpt) throws IOException, URISyntaxException {
        try{
            String path = pathOpt.orElse("volcano.json");
            URL url = this.getClass().getClassLoader().getResource(path);
            String jsonString = new String(Files.readAllBytes(Paths.get(url.toURI())));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            volcanos = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, Volcano.class));
        } catch(Exception e){
            throw(e);
        }
    }

    public Integer numbVolcanoes(){
        return volcanos.size();
    }


    //add methods here to meet the requirements in README.md
    public List<Volcano> eruptedInEighties(){
        return volcanos.stream().filter(e -> e.getYear() >= 1980 && e.getYear() < 1990).collect(Collectors.toList());
    }
    
    public String[] highVEI(){
        return volcanos.stream().filter(vei -> vei.getVEI() >= 6).map(Volcano::getName).toArray(String[] :: new);
    }

    public  float  causedTsunami(){

    float value= volcanos.stream().filter(v-> v.getTsu().equals("tsu")).count();
        return  value / volcanos.size()*100;

   
}

    public String mostCommonType() {
        return  volcanos.stream()
        .collect(Collectors.groupingBy(Volcano::getType, Collectors.counting()))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);

    }

    public Long  eruptionsByCountry(String Country){
 
        return volcanos.stream().filter( v-> v.getCountry().equalsIgnoreCase(Country)).count();
    }
}

