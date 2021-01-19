import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@WebServlet(name = "AuthorizationServlet", urlPatterns = "/authorization")
public class AuthorizationServlet extends HttpServlet {
    private ServletConfig config;
    private List<User> users = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute("users", users);
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        if (!login.isEmpty() && existenceLogin(login)) { // если есть такой логин, то проверяем пароль, нет - регистрируем
            try {
                if (enter(login, password)) {
                    session.setAttribute("userName", login);
                    System.out.println("logged:" + users.get(0).login);
                    voidJsp(req, resp);
                } else
                    // если неверный пароль - обратно на форму входа
                    req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (login.isEmpty())
                    req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                session.setAttribute("userName", register(login, password));
                System.out.println("registered:" + users.get(0).login);
                voidJsp(req, resp);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
    }

    class User {
        private String login;
        private String password;

        private User(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

    }

    public boolean enter(String login, String password) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encodedPassword = Base64.getEncoder().encodeToString(hash);
        users = contextUsers();
        boolean enter = false;
        for (User user : users) {
            if (login.equals(user.getLogin()) &&
                    encodedPassword.equals(user.getPassword())) {
                enter = true;
            }
        }
        return enter;
    }


    public String register(String login, String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encodedPassword = Base64.getEncoder().encodeToString(hash);

        User user = new User(login, encodedPassword);
        users.add(user);
        config.getServletContext().setAttribute("users", users);
        return login;
    }


    private boolean existenceLogin(String login) {
        boolean existence = false;
        users = contextUsers();
        if (users != null) {
            for (User user : users) {
                if (login.equals(user.getLogin())) {
                    existence = true;
                }
            }
        } else {
            users = new ArrayList<>();
            config.getServletContext().setAttribute("users", users);
        }
        return existence;
    }

    private static void voidJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
    }

    private List<User> contextUsers() {
        List<User> users = (List<User>) config.getServletContext().getAttribute("users");
        return users;
    }
}