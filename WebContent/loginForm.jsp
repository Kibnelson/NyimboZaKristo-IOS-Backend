<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>NyimboZaKristo Backend  Application</title>
<link rel="stylesheet" href="css/style.css" type="text/css"></link>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="js/login.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery.validate.js"></script>
<script language="JavaScript" type="text/JavaScript" src="js/mocha.js"></script>
<script type="text/javascript">
$(document).ready(
	function() {
		var oFormObject = document.forms['loginForm'];
		oFormObject.elements["username"].value = "";
		$("#loginFormTwo").validate({
			rules : {
			username : "required",
			inputPassword : {
			required : true,
			minlength : 8
			},
			passwordAg : {
			equalTo : "#inputPassword"
			},
			errorElement : "div",
			wrapper : "div", // a wrapper around the error message
			errorPlacement : function(error,
			element) {
			if (element.hasClass('group')) {
			element = element.parent();
			}
			offset = element.offset();
			error.insertBefore(element)
			error.addClass('message'); // add a class to the wrapper
			error.css('position', 'absolute');
			error.css('left', $(element).position().left+ $(element).width() + 10);
			error.css('top', $(element).position().top);
			}
		}
	});
	
	$("#location").hide();//
	$("#locationE").hide();//
    $("form#loginFormTwo").submit(function() {
           if ($("#loginFormTwo").valid()) {
			dataString = $("#loginFormTwo").serialize();
			$.ajax({
			type : "POST",
			url : "register.do",
			data : dataString,
			success : function(data) {
				if (data == "found") {
				var oFormObject = document.forms['loginFormTwo'];
				oFormObject.elements["username"].value = "";
				oFormObject.elements["inputPassword"].value = "";
				oFormObject.elements["passwordAg"].value = "";
			$('#location .loc').replaceWith("<div id='red'>Username taken<div>");
			$("#location").show("slow");//
			$("#location").delay(3000).hide("slow");
			$("#loginBoxTwo").delay(5000).hide("slow");
			} else if (data == "saved") {
			$('#locationE .loc').replaceWith("<div id='red'>Successfull<div>");
			$("#locationE").show("slow");//
			$("#locationE").delay(3000).hide("slow");
			var oFormObject = document.forms['loginFormTwo'];
			oFormObject.elements["username"].value = "";
			oFormObject.elements["inputPassword"].value = "";
        	oFormObject.elements["passwordAg"].value = "";
     		$("#loginBoxTwo").delay(3000).hide("slow");
		}
	}
});
return false;
}
});
});
</script>
</head>
<body>
	<div id="bar">
		<div id="container">
			<!-- Login Starts Here -->
			<div id="loginContainer">
				<div id="rights">
					<img src="images/logo.png" alt="keys" />
				</div>
				<a href="#" id="loginButton"><span>New </span></a>
				<div style="clear: both"></div>
				<div id="login">
					<div id="icon">
						<img src="images/key.png" alt="keys" />
					</div>
				</div>
				<div id="loginBox">
					<table><tr>
							<td valign="top"><c:if test="${not empty param.login_error}">
									<font color="red"> Invalid user name or password, try
										again.<br /> <br />
									</font>
								</c:if>
							<form id="loginForm" name="login_form"
									action="<c:url value='j_spring_security_check'/>" method="POST">
									<fieldset id="body">
										<LEGEND> </LEGEND>
										<fieldset>
											<label for="email">Username</label> <input type='text'
												id='username' size="30" maxlength="40" name='j_username'
												value='<c:if test="${not empty param.login_error}">
                                                     <c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>
                                                     </c:if>' />
										</fieldset>
										<fieldset>
											<label for="password">Password</label> <input type='password'
												name='j_password' size="30" maxlength="30">
										</fieldset>
										<input type="submit" id="login" value="Sign in" />
									</fieldset>
								</form>
							</td>
					</tr></table>
				</div>
				<div id="loginBoxTwo">
					<DIV id="location"
						class="header-footer ui-state-default ui-corner-all"
						style="padding: 3px 5px 5px; text-align: center; margin-bottom: 1ex;">
						<DIV class="loc">test</DIV>
					</DIV>
					<DIV id="locationE"
						class="header-footer ui-state-default ui-corner-all"
						style="padding: 3px 5px 5px; text-align: center; margin-bottom: 1ex;">
						<DIV class="loc">test</DIV>
					</DIV>
					<form id="loginFormTwo">
						<fieldset id="body">
							<fieldset>
								<label for="email">User name</label> <input type="text"
									name="username" id="username" />
							</fieldset>
							<fieldset>
								<label for="password">Password</label> <input type="password"
									name="inputPassword" id="inputPassword" />
								<div id="complexity" class="default">Strength</div>
							</fieldset>
							<fieldset>
								<label for="password">Retype Password</label> <input
									type="password" name="passwordAg" id="passwordAg" />
							</fieldset>
							<input type="submit" id="login" value="Register" />
						</fieldset>
					</form>
				</div>
			</div>
			<!-- Login Ends Here -->
		</div>
	</div>
</body>
</html>