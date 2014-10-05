<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>NyimboZaKristo Backend Application</title>
<link rel="stylesheet" href="css/layout-default-latest.css"
	type="text/css"></link>
<link rel="stylesheet" href="css/demo_table_jui.css" type="text/css"></link>
<link rel="stylesheet" href="css/style.css" type="text/css"></link>
<script language="JavaScript" type="text/JavaScript" src="js/jquery.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery-ui-latest.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery.layout-latest.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery.layout.resizePaneAccordions-1.0.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/themeswitchertool.js"></script>
<script language="JavaScript" type="text/JavaScript" src="js/debug.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/AutoCountry.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery.ajaxfileupload.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery-ui-timepicker-addon.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/jquery-ui-sliderAccess.js"></script>
<link rel="stylesheet" href="css/jquery-ui-pp.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery.dataTables.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/TableTools.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="js/ZeroClipboard.js"></script>
<link rel="stylesheet" href="css/TableTools.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script type="text/javascript" src="js/jquery.easy-confirm-dialog.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	var ooTableLoc;
	var ooTableLocP;
	var Table;
	myLayout = $('body').layout({
    west__size : 300,
	east__size : 300,
	// RESIZE Accordion widget when panes resiz					,
	west__onresize : $.layout.callbacks.resizePaneAccordions,
	east__onresize : $.layout.callbacks.resizePaneAccordions
	});
	// ACCORDION - in the West pane
	$("#accordion1").accordion({
	fillSpace : true
	});
    // ACCORDION - in the East pane - in a 'content-div'
	$("#accordion2").accordion({
	fillSpace : true,
	active : 1
	});

	$.fn.dataTableExt.oApi.fnReloadAjax = function(
	oSettings, sNewSource) {
	oSettings.sAjaxSource = sNewSource;
	this.fnClearTable(this);
	this.oApi._fnProcessingDisplay(oSettings, true);
	var that = this;
	$.getJSON(
		oSettings.sAjaxSource,
		null,
		function(json) {/* Got the data - add it to the table */
			for ( var i = 0; i < json.aaData.length; i++) {
				that.oApi._fnAddData(
				oSettings,
				json.aaData[i]);
			}
		oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
		that.fnDraw(that);
		that.oApi._fnProcessingDisplay(oSettings, false);
	  });
	}
	// THEME SWITCHER
	$("#themeSwitcher").themeswitcher({
	"loadTheme" : "Pepper Grinder"
	});
	$("#newSongs").validate({
			errorElement : "div",
			wrapper : "div", // a wrapper around the error message
			errorPlacement : function(error, element) {
			if (element.hasClass('group')) {
					element = element.parent();
			}
			offset = element.offset();
			error.insertBefore(element)
			error.addClass('message'); // add a class to the wrapper
			error.css('position', 'absolute');
			error.css('left',
			$(element).position().left+ $(element).width()+ 10);
				error.css('top',
				$(element).position().top);
			}
	});
	$("#locationError").hide();//
	$("#location").hide();//
	$("#cancel").hide();//
	$("#newSong").hide();
	$("#Batchsetter").tabs();
	$("#Batchsetterone").tabs();
	$("#newSongdiv").tabs();
	$("#viewSongdiv").tabs();
	$("#viewEntdiv").tabs();
	ooTableLoc = $('#tsongs').dataTable({
		bJQueryUI : true,
		bRetrieve : true,
		bServerSide : true,
 		bAutoWidth : false,
		bProcessing : true,
		"fnRowCallback" : function(nRow,
		aData, iDisplayIndex) {
		$('td:eq(0)', nRow).html('<img  id="edit" src="images/edit2.png" alt="keys"/>');
		$('td:eq(4)', nRow).html('<img id="delete" src="images/delete.png" alt="keys"/>');
		return nRow;
		},
		sAjaxSource : 'getSongs.do',
		"aoColumnDefs" : [ {
			"bVisible" : false,
			"aTargets" : [ 4,5 ]
		} ]
		});
	$("#vSongs").click(function() {
		$("#viewSong").show();//
		$("#viewEnt").hide();
		$("#newSong").hide();
		$('#tsongs').dataTable().fnReloadAjax('getSongs.do');
	});
	$("#dsongs").click(function() {
		var oFormObject = document.forms['newSongs'];
		oFormObject.elements["id"].value = "";
		oFormObject.elements["edit"].value = "false";
		oFormObject.elements["title"].value = "";
		oFormObject.elements["song"].value = "";
		$("#newSong").show();
		$("#viewSong").hide();
		$('#tsongs')
		.dataTable().fnReloadAjax('getSongs.do');
	});
	$('#tsongs').delegate('tbody td #edit','click',function() {
		$("#cancel").show();//
		var editTr = this.parentNode.parentNode;
		var aData = ooTableLoc.fnGetData(editTr);
		var oFormObject = document.forms['newSongs'];
		oFormObject.elements["id"].value = aData[1];
		oFormObject.elements["edit"].value = "true";
		oFormObject.elements["title"].value = aData[2];
		oFormObject.elements["song"].value = aData[3];
		$("#newSong").show("slow");//
	});
	$('#tsongs').delegate('tbody td #delete','click',function() {
		var editTr = this.parentNode.parentNode;
		var aData = ooTableLoc.fnGetData(editTr);
		var oFormObject = document.forms['newSongs'];
		oFormObject.elements["id"].value = aData[1];
		var isOK = confirm("Are you sure to delete?");
		var dataString = "id=" + aData[1];
		if (isOK) {
			$.ajax({
				type : "POST",
				url : "deleteSongs.do",
				data : dataString,
				success : function(
						data) {
						if (data == "found") {
						$('#location .loc').replaceWith("<div id='red'>Deleted<div>");
						$("#location").show("slow");//
						$("#location").delay(3000).hide("slow");
						}
						$("#newSong").hide();
						$("#viewSong").show();
						$('#tsongs').dataTable().fnReloadAjax('getSongs.do');
				}
			});
		}
	});
	$("form#newSongs").submit(function() {
		if ($("#newSongs").valid()) {
			dataString = $("#newSongs").serialize();
			$.ajax({
				type : "POST",
				url : "saveSongs.do",
				data : dataString,
				success : function(data) {
					if (data == "found") {
						$('#location .loc').replaceWith("<div id='red'>Location already entered<div>");
						$("#location").show("slow");//
						$("#location").delay(3000).hide("slow");
					} else {
						$('#locationError .loc').replaceWith("<div id='red'>Data saved<div>");
						$("#locationError").show("slow");//
						$("#locationError").delay(5000).hide("slow");
					}
    			var oFormObject = document.forms['newSongs'];
				oFormObject.elements["id"].value = "";
				oFormObject.elements["edit"].value = "false";
 				oFormObject.elements["title"].value = "";
				oFormObject.elements["song"].value = "";
				$("#newSong").hide();
				$("#cancel").hide();
				$("#viewSong").show();
				$('#tsongs').dataTable().fnReloadAjax('getSongs.do');
			}
		});
	return false;
	}
	});
});
</script>
</head>
<body>
	<DIV id="center" class="ui-layout-center">
		<DIV id="incss">
			<DIV id="locationError"
				class="header-footer ui-state-default ui-corner-all"
				style="padding: 3px 5px 5px; text-align: center; margin-bottom: 1ex;">
			</DIV>
			<DIV id="newSong">
				<DIV id="Batchsetterone">
					<DIV class="header-footer ui-state-default ui-corner-all"
						style="padding: 3px 5px 5px; text-align: center; margin-bottom: 1ex;">
						New Songs</DIV>
					<UL tyle="-moz-border-radius-bottomleft: 0; -moz-border-radius-bottomright: 0;">
						<LI><A href="#tab_1"><SPAN>Add </SPAN> </A></LI>
					</UL>
					<DIV class="ui-layout-content ui-widget-content ui-corner-bottom"
						style="border-top: 0; padding-bottom: 1em;">
						<DIV id="tab_1batch">
							<form id="newSongs" action="#">
								<label for="engine">Song Title</label><input type="text"
									name="title" id="title" class="required" /><br /> <label
									for="engine">song Lyrics</label>
									
									<textarea class="required" id="song" cols="50" rows="15" name="song"></textarea><br /> 
									<input type="hidden" name="id" id="id" />
								<input type="hidden" name="edit" id="edit" value="false" /> <input
									class="submit" id="loginButton" type="submit" value="Submit" />
							</form>
						</DIV>
					</DIV>
				</DIV>
			</DIV>
			<!-- <h3>File Upload:</h3>
			Select a file to upload: <br />
			<form action="UploadFile" method="post" enctype="multipart/form-data">
				<input type="file" name="file" size="50" /> <input type="hidden"
					name="id" value="test" id="id" /> <input type="text" name="id2"
					value="test" id="id" /> <br /> <input type="submit"
					value="Upload File" /> -->
				<DIV id="viewSong">
					<DIV id="viewSongdiv">
						<DIV class="header-footer ui-state-default ui-corner-all"
							style="padding: 3px 5px 5px; text-align: center; margin-bottom: 1ex;">
							Saved Songs</DIV>
						<UL
							style="-moz-border-radius-bottomleft: 0; -moz-border-radius-bottomright: 0;">
							<LI><A href="#tab_1"><SPAN>Songs</SPAN> </A></LI>
						</UL>
						<DIV class="ui-layout-content ui-widget-content ui-corner-bottom"
							style="border-top: 0; padding-bottom: 1em;">
							<DIV id="tab_1batch">
								<table cellpadding="0" cellspacing="0" border="0"
									class="display" id="tsongs">
									<thead>
										<tr>
											<th width="4%">Edit Song</th>
											<th>Song Number</th>
											<th>Song Title</th>
											<th>Song Lyric</th>
											<th>Audio</th>
											<th>Video</th>
											<th>Delete Song</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</tbody>

								</table>
							</DIV>

						</DIV>
					</DIV>

				</DIV>
		</DIV>
	</DIV>
	</DIV>
	<DIV id="north" class="ui-layout-north">

		<div id="rightstwo">
			<img width="150" height="50" src="images/logo.png" alt="keys" />
		</div>
		<div id="leftss">
			<a href="<c:url value="/j_spring_security_logout" /> "
				id="loginButton"> Logout</a>
		</div>
	</DIV>
	<DIV class="ui-layout-west">
		<div id="accordion1" class="basic">
			<h3>
				<a href="#">Admin</a>
			</h3>
			<div class="ui-layout-content">
				<UL>
					<LI><A href="#" id="dsongs">Create Songs</A></LI>
					<LI><A href="#" id="vSongs">View Songs</A></LI>
				</UL>
			</div>
		</div>
	</DIV>
</body>
</html>