package com.abc.controller.Admin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.abc.model.MenuItem;
import com.abc.service.MenuItemService;

/**
 * Servlet implementation class DashboardMenuController
 */
@MultipartConfig // Enables file upload
public class DashboardMenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MenuItemService menuItemService;
	
	private String mainFile = "WEB-INF/view/admin/dashboardmenu.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardMenuController() {
		super();
		// TODO Auto-generated constructor stub
		menuItemService = MenuItemService.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		listMenuItems(request, response);
		
		String action = request.getParameter("action");
		
		if ((action != null) &&action.equals("editMenuItem")) {
			int id = Integer.parseInt(request.getParameter("menuItemId"));
	        try {
	            MenuItem menuItem = menuItemService.getMenuItemById(id);
	            request.setAttribute("menuItem", menuItem);
	        } catch (SQLException e) {
	            request.setAttribute("errorMessage", e.getMessage());
	            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
	        }
		}		
		
		request.getRequestDispatcher(mainFile).forward(request, response);
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String action = request.getParameter("action");
		if(action != null)
		{
			if (action.equals("addMenuItem")) {
				addMenuItem(request, response);
			}else if (action.equals("updateMenuItem")) {
				updateMenuItem(request, response);
			}else if (action.equals("deleteMenuItem")) {
				deleteMenuItem(request, response);
			}		
		}
		
		doGet(request, response);
	}

	private void addMenuItem(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String name = request.getParameter("AddMenuItemname");
	    BigDecimal price = new BigDecimal(request.getParameter("AddMenuItemprice"));
	    String description = request.getParameter("AddMenuItemdescription");
	    String category = request.getParameter("AddMenuItemcategory");

	    // Handle file upload for the image
	    Part filePart = request.getPart("AddMenuItemimage");
	    String fileName = extractFileName(filePart);
	    
	    // Generate a unique file name using timestamp
	    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
	    
	    String uploadPath = getServletContext().getRealPath("") + File.separator + "images/menu";
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdir();
	    }
	    
	    String filePath = uploadPath + File.separator + uniqueFileName;
	    filePart.write(filePath);

	    // Save image path in database
	    String imagePath = "images/menu" + File.separator + uniqueFileName;

	    MenuItem menuItem = new MenuItem(name, description, price, imagePath, category);
	    menuItemService.addMenuItem(menuItem);
	}


	private String extractFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		for (String content : contentDisposition.split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private void listMenuItems(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<MenuItem> menuItems = menuItemService.getAllMenuItems();
			request.setAttribute("menuItems", menuItems);
			//request.getRequestDispatcher("WEB-INF/view/listMenuItems.jsp").forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
		}
	}
	
	private void updateMenuItem(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    int id = Integer.parseInt(request.getParameter("UpdateMenuid"));
	    String name = request.getParameter("AddMenuItemname");
	    BigDecimal price = new BigDecimal(request.getParameter("AddMenuItemprice"));
	    String description = request.getParameter("AddMenuItemdescription");
	    String category = request.getParameter("AddMenuItemcategory");

	    // Handle file upload for the image
	    Part filePart = request.getPart("AddMenuItemimage");
	    String fileName = extractFileName(filePart);
	    
	    String uploadPath = getServletContext().getRealPath("") + File.separator + "images/menu";
	    String imagePath = null;
	    
	    // Retrieve the existing menu item from the database
	    MenuItem existingMenuItem = null;
	    try {
	        existingMenuItem = menuItemService.getMenuItemById(id);
	    } catch (SQLException e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
	        return;
	    }

	    // If a new image is uploaded
	    if (fileName != null && !fileName.isEmpty()) {
	        // Generate a unique file name using timestamp
	        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
	        imagePath = "images/menu" + File.separator + uniqueFileName;

	        // Delete the existing image file if it exists
	        String existingImagePath = existingMenuItem.getImagePath();
	        if (existingImagePath != null && !existingImagePath.isEmpty()) {
	            File existingImageFile = new File(getServletContext().getRealPath("") + File.separator + existingImagePath);
	            if (existingImageFile.exists()) {
	                existingImageFile.delete();
	            }
	        }

	        // Write the new image file
	        String newFilePath = uploadPath + File.separator + uniqueFileName;
	        filePart.write(newFilePath);
	    } else {
	        // Use the existing image path if no new image is uploaded
	        imagePath = existingMenuItem.getImagePath();
	    }

	    // Update the menu item in the database
	    MenuItem menuItem = new MenuItem(id, name, description, price, imagePath, category);
	    menuItemService.updateMenuItem(menuItem);

	    // Redirect or forward as needed
	    //response.sendRedirect("menuItem?action=list");
	}


	
	private void deleteMenuItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int id = Integer.parseInt(request.getParameter("menuItemId"));
	    
	    try {
	        // Get the menu item to delete
	        MenuItem menuItem = menuItemService.getMenuItemById(id);
	        
	        if (menuItem != null) {
	            // Delete the image file from the server
	            String imagePath = getServletContext().getRealPath("") + File.separator + menuItem.getImagePath();
	            File imageFile = new File(imagePath);
	            if (imageFile.exists()) {
	                imageFile.delete();
	            }
	            
	            // Delete the menu item from the database
	            menuItemService.deleteMenuItem(id);
	        }
	        
	        //response.sendRedirect("DashboardMenu"); // Redirect back to the menu list
	    } catch (SQLException e) {
	        request.setAttribute("errorMessage", e.getMessage());
	        request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
	    }
	}

}
