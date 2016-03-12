$(document).ready(function(){
	
	if ($('#season').length) {
		populateTeamDropdowns();
	}

	$("#logout").click(function(){
		$( "#signout-form" ).submit();
	});
	
	$('#season').change(populateTeamDropdowns);
	
	$("button[value^='visitor:']").each(function() {
		$(this).click(function() {
			  addLineupOrderRow($(this));
		});
	});
	
	$("button[value^='delete_visitor:']").each(function() {
		$(this).click(function() {
			deleteLineupOrderRow($(this));
		});
	});
	
	$("button[value^='home:']").each(function() {
		$(this).click(function() {
			  addLineupOrderRow($(this));
		});
	});
	
	$("button[value^='delete_home:']").each(function() {
		$(this).click(function() {
			deleteLineupOrderRow($(this));
		});
	});	

    var config = {
      '.chosen-select'           : {},
      '.chosen-select-deselect'  : {allow_single_deselect:true},
      '.chosen-select-no-single' : {disable_search_threshold:10},
      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
      '.chosen-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
  
	
	function addLineupOrderRow(button) {
		  var addButtonValueArray = button.val().split(":");
		  whichTeam = addButtonValueArray[0];
		  lineupOrderPosition = Number(addButtonValueArray[1]);
		  lineupOrderIndex = Number(addButtonValueArray[2]);
		  
		  var newRow = button.closest('tr').clone(true, true);
		  
		  // Remove chosen select in cloned new row
		  var newRowSecondTd = newRow.find('td:nth-child(2)');
		  newRowSecondTd.empty();
		  newRowSecondTd.prepend($('<select data-placeholder="Choose a Player..." style="width:250px;"></select>'));
		  
		  // Copy id and name attributes from the original select to the new select
		  selectedRowSelect = button.closest('tr').find('td:nth-child(2)').find('select');
		  var newSelect = newRowSecondTd.find("select");
		  newSelect.attr('id', selectedRowSelect.attr('id'));
		  newSelect.attr('name', selectedRowSelect.attr('name'));
		  
		  // Copy the select options from the original select to the new select
		  selectedRowSelect.find("option").each(function() {
			    newSelect.append($(this).clone());
		  });
		  
		  // Clear any selected options in the new select.
		  newSelect.find("option").prop("selected", false);
		  // Clear all input fields in the new row.
		  newRow.find("input").val("");
		  
		  var newRowLineupOrderIndex = lineupOrderIndex + 1;
		  updateRowIndex(newRow, whichTeam, lineupOrderPosition, lineupOrderIndex, newRowLineupOrderIndex);
		  
		  // Increment indexes on all following players in same line up order position
		  
		  // Calculate the add button value for the following lineup order position. We will stop when the value is equal to this on the add button.
		  var followingLineupOrderPosition = lineupOrderPosition + 1;
		  var followingLineupOrderAddButtonValue = whichTeam + ":" + followingLineupOrderPosition + ":0";
		  
		  // Set the line up order position and index for the next player in this lineup order position.
		  var playerMoveUpLineupOrderPosition = lineupOrderPosition;
		  var playerMoveUpLineupOrderIndex = newRowLineupOrderIndex + 1;
		  
		  button.closest('tr').nextAll().each(function() {
			  // Stop when we get to a row for the following lineup order position
			  if ($(this).find("button[value='" + followingLineupOrderAddButtonValue + "']").length != 0) {
				  return false;
			  }
			  
			  updateRowIndex($(this), whichTeam, playerMoveUpLineupOrderPosition, playerMoveUpLineupOrderIndex - 1, playerMoveUpLineupOrderIndex);
			  playerMoveUpLineupOrderIndex = playerMoveUpLineupOrderIndex + 1;
		  });
		  
		  button.closest('tr').after($(newRow));
		  newRow.find('select').each(function(){
			  $(this).addClass('chosen-select');
			  $(this).chosen({});
			});
		   updateLineupOrderPositionSpanVisibility(whichTeam, lineupOrderPosition);
		   updateLineupOrderCellBorders(whichTeam, lineupOrderPosition);
	}
	
	function deleteLineupOrderRow(button) {
		var deleteButtonValueArray = button.val().split(":");
		whichButtonAction = deleteButtonValueArray[0];
		whichButtonActionArray = whichButtonAction.split("_");
		whichTeam = whichButtonActionArray[1];
		lineupOrderPosition = Number(deleteButtonValueArray[1]);
		lineupOrderIndex = Number(deleteButtonValueArray[2]);
		
		if (button.closest('table').find("button[value^='delete_" + whichTeam + ":" + lineupOrderPosition  + ":']").length == 1) {
			alert("You cannot delete the only player in a lineup order position");
			return false;
		}
		
		// Decrement indexes on all following players in same line up order position
		var deleteButtonValuePrefix = "delete_" + whichTeam + ":";
		  
		// Calculate the delete button value for the following lineup order position. We will stop when the value is equal to this on the delete button.
		var followingLineupOrderPosition = lineupOrderPosition + 1;
		var followingLineupOrderDeleteButtonValue = deleteButtonValuePrefix + followingLineupOrderPosition + ":0";
		
		// Set the line up order position and index for the next player in this lineup order position.
		var playerMoveDownLineupOrderPosition = lineupOrderPosition;
		var playerMoveDownLineupOrderIndex = lineupOrderIndex;
		
		button.closest('tr').nextAll().each(function() {
			  // Stop when we get to a row for the following lineup order position
			  if ($(this).find("button[value='" + followingLineupOrderDeleteButtonValue + "']").length != 0) {
				  return false;
			  }
			  
			  updateRowIndex($(this), whichTeam, playerMoveDownLineupOrderPosition, playerMoveDownLineupOrderIndex + 1, playerMoveDownLineupOrderIndex);
			  playerMoveDownLineupOrderIndex = playerMoveDownLineupOrderIndex + 1;
		});
		
	    button.closest('tr').remove();
	    updateLineupOrderPositionSpanVisibility(whichTeam, lineupOrderPosition);
	    updateLineupOrderCellBorders(whichTeam, lineupOrderPosition);
	}
	
	function updateRowIndex(row, whichTeam, lineupOrderPosition, oldLineupOrderIndex, newLineupOrderIndex) {
		  addButtonValuePrefix = whichTeam + ":";
		  oldAddButtonValue = addButtonValuePrefix + lineupOrderPosition + ":" + oldLineupOrderIndex;
		  newAddButtonValue = addButtonValuePrefix + lineupOrderPosition + ":" + newLineupOrderIndex;
		  // update index on Add button
		  row.find("button[value='" + oldAddButtonValue + "']").val(newAddButtonValue);
		  // update index on Delete button 
		  row.find("button[value='" + "delete_" + oldAddButtonValue + "']").val("delete_" + newAddButtonValue);
		  
		  // update index on player select id attribute
		  var oldPlayerSelectId = whichTeam + "LineupOrders" + (lineupOrderPosition - 1) + ".scoresheetPlayers" + oldLineupOrderIndex + ".playerId";
		  var newPlayerSelectId = whichTeam + "LineupOrders" + (lineupOrderPosition - 1) + ".scoresheetPlayers" + newLineupOrderIndex + ".playerId";
		  row.find("select[id = '" + oldPlayerSelectId + "']").attr('id', newPlayerSelectId);
		  
		  // update index on player select name attribute
		  var oldPlayerSelectName = 
			  whichTeam + "LineupOrders[" + (lineupOrderPosition - 1) + "].scoresheetPlayers[" + oldLineupOrderIndex + "].playerId";
		  var newPlayerSelectName = 
			  whichTeam + "LineupOrders[" + (lineupOrderPosition - 1) + "].scoresheetPlayers[" + newLineupOrderIndex + "].playerId";
		  row.find("select[name = '" + oldPlayerSelectName + "']").attr('name', newPlayerSelectName);
		  
		  // update index on chosen div
		  var oldPlayerChosenId = whichTeam + "LineupOrders" + (lineupOrderPosition - 1) + "_scoresheetPlayers" + oldLineupOrderIndex + "_playerId_chosen";
		  var newPlayerChosenId = whichTeam + "LineupOrders" + (lineupOrderPosition - 1) + "_scoresheetPlayers" + newLineupOrderIndex + "_playerId_chosen";
		  row.find("div[id = '" + oldPlayerChosenId + "']").attr('id', newPlayerChosenId);
		  
		  var fieldNames = ["atBats", "runs", "hits", "rbi", "doubles", "triples", "homeruns", "stolenBases"];
		  var i;
		  var fieldName;
		  var len = fieldNames.length;
		  
		  for (i = 0; i < len; i++) {
			  fieldName = fieldNames[i];
			  
			  // update index on field id attribute
			  var oldInputId = whichTeam + "LineupOrders" + (lineupOrderPosition - 1) + ".scoresheetPlayers" + oldLineupOrderIndex + "." + fieldName;
			  var newInputId = whichTeam + "LineupOrders" + (lineupOrderPosition - 1) + ".scoresheetPlayers" + newLineupOrderIndex + "." + fieldName;
			  row.find("input[id = '" + oldInputId + "']").attr('id', newInputId);
			  
			  // update index on at field name attribute
			  var oldInputName = 
				  whichTeam + "LineupOrders[" + (lineupOrderPosition - 1) + "].scoresheetPlayers[" + oldLineupOrderIndex + "]." + fieldName;
			  var newInputName = 
				  whichTeam + "LineupOrders[" + (lineupOrderPosition - 1) + "].scoresheetPlayers[" + newLineupOrderIndex + "]." + fieldName;
			  row.find("input[name = '" + oldInputName + "']").attr('name', newInputName);
		  }
		  
		  // update index on lineuporder span
		  var oldLineupOrderSpanId = whichTeam + "_lineuporder:" + lineupOrderPosition + ":" + oldLineupOrderIndex;
		  var newLineupOrderSpanId = whichTeam + "_lineuporder:" + lineupOrderPosition + ":" + newLineupOrderIndex;
		  row.find("span[id = '" + oldLineupOrderSpanId + "']").attr('id', newLineupOrderSpanId);
		  
		  // update index on row id
		  var newRowId = whichTeam + "_row:" + lineupOrderPosition + ":" + newLineupOrderIndex;
		  row.attr('id', newRowId);
	}
	
	function updateLineupOrderPositionSpanVisibility(whichTeam, lineupOrderPosition) {
		matchingIdPrefix = whichTeam + "_lineuporder:" + lineupOrderPosition + ":";
		$("span[id^='" + matchingIdPrefix + "']").each(function() {
			idArray = $(this).attr('id').split(":");
			lineupOrderIndex = Number(idArray[2]);
			if (lineupOrderIndex == 0) {
				$(this).css("display", "block");
			} else {
				$(this).css("display", "none");
			}
		});
	}
	
	function updateLineupOrderCellBorders(whichTeam, lineupOrderPosition) {
		matchingIdPrefix = whichTeam + "_row:" + lineupOrderPosition + ":";
		console.log("updateLineupOrderCellBorders(" + whichTeam + ", " + lineupOrderPosition + ")");
		$("tr[id^='" + matchingIdPrefix + "']").each(function() {
			idArray = $(this).attr('id').split(":");
			console.log("idArray: " + idArray);
			lineupOrderIndex = Number(idArray[2]);
			console.log("lineupOrderIndex: " + lineupOrderIndex);
			$(this).find("td").each(function() {
				console.log($(this).html());
				if (lineupOrderIndex == 0) {
					$(this).removeClass("scoresheet-normal-cell");
					$(this).addClass("scoresheet-top-lineuporder-cell");
				} else {
					$(this).removeClass("scoresheet-top-lineuporder-cell");
					$(this).addClass("scoresheet-normal-cell");
				}
			});                       
		});
	}
	 
	function populateTeamDropdowns() {
		if( !$('#season').val() ) {
			return;
		}
		url = '/bbstats/v1/seasons/' + $('#season').val()  + '/teams';
		$.getJSON(url, {
			ajax : 'true'
		}, function(data) {
			$( "select[id$='TeamName']" ).each(function() {
				  selectedVal = $(this).val();
				  var html = '<option value="">Select</option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].name + '"';
						if (data[i].name == selectedVal) {
							html += ' selected';
						}
						html += '>';
						html += data[i].location + ' ' + data[i].name + '</option>';
					}
					html += '</option>';
				  $(this).html(html);
			});
			
		});
	}
	
	function replaceAll(str, find, replace) {
		 return str.replace(new RegExp(find, 'g'), replace);
	}
	
	function escapeRegExp(str) {
	  return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
	}
});