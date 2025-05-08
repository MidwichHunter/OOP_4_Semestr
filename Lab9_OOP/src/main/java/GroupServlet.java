import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

@WebServlet("/GroupServlet")
public class GroupServlet extends HttpServlet {
    private static final String filePath = "C:\\Users\\79836\\IdeaProjects\\Lab9_OOP\\Hunters.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String rang = request.getParameter("rang");
        int age = Integer.parseInt(request.getParameter("age"));
        String group = request.getParameter("group");
        String pdaNumber = request.getParameter("pda_number");
        Hunter hunter = new Hunter(name, rang, age, group, pdaNumber);
        JSONArray hunterList = getHunterListFromFile();
        hunterList.add(hunter.toJSON());
        writeHunterListToFile(hunterList);
        response.sendRedirect("/Lab9_OOP_war_exploded");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray hunterList = getHunterListFromFile();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body style=\"background-color: #0a58ca\"><div><table class=\"table\"><thead><tr><th scope=\"col\">Имя</th><th scope=\"col\">Ранг</th><th scope=\"col\">Возраст</th> <th scope=\"col\">Группировка</th><th scope=\"col\">Номер ПДА</th></tr></thead>");
        for (Object obj : hunterList) {
            JSONObject hunterJSON = (JSONObject) obj;
            Hunter hunter = Hunter.fromJSON(hunterJSON);
            out.println("<tbody> <tr><td>" + hunter.getName() + "</td><td>" + hunter.getRang() + "</td><td>" + hunter.getAge() + "</td><td>" + hunter.getGroup() + "</td><td>" + hunter.getPdaNumber() + "</td>");
        }
        out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
    }
    private JSONArray getHunterListFromFile() {
        try {
            JSONParser parser = new JSONParser();
            File file = new File(filePath);
            String fullPath = file.getAbsolutePath();
            System.out.println(fullPath);
            return (JSONArray) parser.parse(new FileReader(filePath));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
    private void writeHunterListToFile(JSONArray hunterList) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(hunterList.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}