<html>
  <head>
    <title>StarBox</title>
	<link rel="stylesheet" href="/starbox/css/main.css" />
	<link rel="stylesheet" href="/starbox/css/users.css" />
  </head>
  <body>
  	<jsp:include page="_header.jsp" />
    <div class="main">
      <div class="layout">
        <div class="users-container clearfix">
        	<div class="left">
	        	<h2>Add new user</h2>
	        	<%  if(!(request.getAttribute("addedUser") == null)){
	        			out.println("Added user: "+request.getAttribute("addedUser"));
	        		}
	        	%>
	          	<form method="POST" action="?action=create" name="create">
	          		<input type="textfield" name="ip" value="1.2.3.4"/>
	          		<input type="submit" value="Create"/>    		
	          	</form>
          	</div>
        </div>
      </div>
    </div>
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/search.js"></script>
  </body>
</html>
