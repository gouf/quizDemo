package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List list;
	ResultSet rs;
	QuizSql quizsql;
	private final int MAX_QUIZ_NUM = 3;
	String quiz;
	String answer;
	String miss1;
	String miss2;
	String miss3;
	String hint;

    public QuizServlet() {
        super();
        quizsql = new QuizSql();
        list = new ArrayList();
        for (int i = 1; i <= MAX_QUIZ_NUM; i++) {
        	list.add(i);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append("クイズ・開始ッ！！");
		try {
			rs = quizsql.getQuizData(getTargetNumber());

			while(rs.next()) {
				quiz = rs.getString(2);
				answer = rs.getString(3);
				miss1 = rs.getString(4);
				miss2 = rs.getString(5);
				miss3 = rs.getString(6);
				hint = rs.getString(7);

				request.setAttribute("quiz", quiz);
				request.setAttribute("answer", answer);
				request.setAttribute("miss1", miss1);
				request.setAttribute("miss2", miss2);
				request.setAttribute("miss3", miss3);
				request.setAttribute("hint", hint);

				// result.jsp にページ遷移
				RequestDispatcher dispatch = request.getRequestDispatcher("./quiz.jsp");
				dispatch.forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

    public int getTargetNumber() {
    	int targetNum = 0;
    	Random random = new Random();
    	targetNum = random.nextInt(MAX_QUIZ_NUM) + 1;

    	list.remove(targetNum - 1);
    	return targetNum;
    }

}
