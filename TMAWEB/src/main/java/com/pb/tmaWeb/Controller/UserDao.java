package com.pb.tmaWeb.Controller;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pb.tmaWeb.Config.HibernateUtils;
import com.pb.tmaWeb.Model.User;

public class UserDao{
	private static SessionFactory factory; 
	
//	public static void main(String[] agrs){
//		
//		UserDao userDao = new UserDao();
//		
//		User user1 = new User();
//		
//		user1.setName("admin");
//		user1.setPasswordHash("123");
//		userDao.addUser(user1);
//		System.out.print(userDao.checkLogin("admin", "123"));
//		System.out.println(userDao.userList());
//	}
	
	public UserDao(){
		factory = HibernateUtils.getSessionFactory();
	}
	
	public void addUser(User user) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
	         tx = session.beginTransaction();
	         session.persist(user);
	         tx.commit();
	    }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	    }finally {
	         session.close(); 
	    }
   }
   
   public Boolean checkLogin(String username, String password){
	   Session session = factory.openSession();
	   Boolean result = false;
	   Query query = session.createQuery("FROM User as u where u.name=? and u.passwordHash=?");
	   query.setParameter(0, username);
	   query.setParameter(1, password);
	   List list = query.list();
	   if((list!=null) && (list.size()>0)){
		   result = true;
	   }
	   session.close();
	   return result;
   }
	
   @SuppressWarnings("unchecked")
	public List<User> userList( ){
		  List<User> users = null;
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         users = session.createQuery("FROM User").list();
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return users;
   }
   
   public void updateUser(int userID, String userName, String password, int role){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         User user = (User)session.get(User.class, userID); 
         user.setName(userName);
         user.setPasswordHash(password);
         user.setRole(role);
         
         session.update(user);
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }

}
