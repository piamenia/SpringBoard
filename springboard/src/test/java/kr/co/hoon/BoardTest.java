package kr.co.hoon;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardTest {
	@Inject
	private DataSource ds;
	@Test
	public void testConection()throws Exception{
		Connection con = null;
		try {
			con = ds.getConnection();
			System.out.println("연결객체:" +con);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}	
	}
}
