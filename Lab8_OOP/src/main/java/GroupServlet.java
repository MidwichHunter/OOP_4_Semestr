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
    private static final String filePath = "C:\\Users\\79836\\IdeaProjects\\Lab8\\Hunters.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String rang = request.getParameter("rang");
        int age = Integer.parseInt(request.getParameter("age"));
        String group = request.getParameter("group");
        String pda_number = request.getParameter("pda_number");

        JSONObject hunter = new JSONObject();
        hunter.put("name", name);
        hunter.put("rang", rang);
        hunter.put("age", age);
        hunter.put("group", group);
        hunter.put("pda_number", pda_number);

        JSONArray huntersList = new JSONArray();

        try {
            JSONParser parser = new JSONParser();
            File file = new File(filePath);
            String fullPath = file.getAbsolutePath();
            System.out.println(fullPath);
            if (file.exists()) {
                huntersList = (JSONArray) parser.parse(new FileReader(filePath));
            }
            huntersList.add(hunter);
            System.out.println("Hunters List: " + huntersList);
            FileWriter fileWriter = new FileWriter(filePath);

            fileWriter.write(huntersList.toJSONString());
            fileWriter.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Lab8_OOP_war_exploded");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JSONParser parser = new JSONParser();
            JSONArray huntersList = (JSONArray) parser.parse(new FileReader(filePath));

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body style=\"background-color: #0a58ca\"><div><table class=\"table\"><thead><tr><th scope=\"col\">Имя</th><th scope=\"col\">Ранг</th><th scope=\"col\">Возраст</th> <th scope=\"col\">Группировка</th><th scope=\"col\">Номер ПДА</th></tr></thead>");
            for (Object obj : huntersList) {
                JSONObject hunter = (JSONObject) obj;
                out.println("<tbody> <tr><td>" + hunter.get("name") + "</td><td>" + hunter.get("rang") + "</td><td>" + hunter.get("age") + "</td><td>" + hunter.get("group") + "</td><td>" + hunter.get("pda_number") + "</td>");
            }

            out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}