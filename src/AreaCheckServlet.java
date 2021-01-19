import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static jdk.nashorn.internal.runtime.JSType.isNumber;

@WebServlet(name = "AreaCheckServlet", urlPatterns = "/checking")
public class AreaCheckServlet extends HttpServlet {

    private ServletConfig config;
    private List<Point> list = null;


    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void destroy() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder data = new StringBuilder();
        if (list != null) {
            printPoints(data);
        } else {
            data.append("No Data");
        }
        request.setAttribute("data", data.toString());
        getServletContext().getRequestDispatcher("/results.jsp").forward(request, response);
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        double X = 0;
        double Y = 0;
        int R = 0;

        try {

            X = Double.parseDouble(nullValid("X", request, response));
            if (!isNumber(X) || X > 4 || X < -4) voidJsp(request, response);
        } catch (NumberFormatException e) {
            voidJsp(request, response);
        }

        try {
            Y = Double.parseDouble(nullValid("Y", request, response));
            if (!isNumber(Y) || Y >= 5 || Y <= -5) voidJsp(request, response);
        } catch (
                NumberFormatException e) {
            voidJsp(request, response);
        }

        try {
            R = Integer.parseInt(nullValid("R", request, response));
            if (!isNumber(R) || R < 1 || R > 5) voidJsp(request, response);
        } catch (
                NumberFormatException e) {
            voidJsp(request, response);
        }

        if (list == null) {
            list = new ArrayList<>();
            config.getServletContext().setAttribute("list", list);
        }

        try {
            Point p = new Point(X, Y, R);
            p.isInArea = checkArea(p.x, p.y, p.r);
            list.add(p);
        } catch (
                Exception e) {
            e.printStackTrace();
            request.getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
        }

        if (request.getParameter("silent") != null && request.getParameter("silent").

                equals("on")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print("{" + "\"in_area\":" + (list.get(list.size() - 1).isInArea ? "true" : "false") + "}");
            out.flush();
        } else {

            StringBuilder data = new StringBuilder();
            printPoints(data);

            request.setAttribute("data", data.toString());
            getServletContext().getRequestDispatcher("/results.jsp").forward(request, response);
        }
    }


    private static boolean checkArea(double x, double y, int R) {
        if (x >= 0 && y >= 0 && y <= R && x <= R) {
            return true;
        }
        if (x <= 0 && y >= 0 && (y - 0.5 * x <= 0.5 * R)) {
            return true;
        }
        if (x <= 0 && y <= 0 && (pow(y, 2) + pow(x, 2) <= pow(R * 0.5, 2))) {
            return true;
        }
        return false;
    }

    public static class Point {
        double x;
        double y;
        int r;
        boolean isInArea;

        Point(double x, double y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }

    }

    private static String nullValid(String parameter, HttpServletRequest request, ServletResponse response) throws ServletException, IOException {
        if (request.getParameter(parameter) == null) {
            request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            parameter = request.getParameter(parameter).replace(',', '.').trim();
        }
        return parameter;
    }

    private static void voidJsp(HttpServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
    }

    private void printPoints(StringBuilder data) {
        for (Point p : list) {
            data.append("<tr>");

            data.append("<td>");
            data.append(String.format("%.2f", p.x));
            data.append("</td>");

            data.append("<td>");
            data.append(String.format("%.2f", p.y));
            data.append("</td>");

            data.append("<td>");
            data.append(String.format("%d", p.r));
            data.append("</td>");

            data.append("<td>");
            data.append(p.isInArea ? "Yes" : "No");
            data.append("</td>");

            data.append("</tr>");
        }
    }
}
