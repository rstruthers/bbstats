$(document).ready(function(){
	
	if ($('#season').length) {
		populateTeamDropdowns();
	}

	$("#logout").click(function(){
		$( "#signout-form" ).submit();
	});
	
	$('#season').change(populateTeamDropdowns);
	
	/**
	$( "button[id^='vp:']" ).each(function() {
		$(this).click(function() {
			  alert( "Handler for .click() called");
			  var vpArray = $(this).attr('id').split(":");
			  alert("vpArray: " + vpArray)
			  var nextIndex = Number(vpArray[2]) + 1;
			  var newRow = $("tr[id='" +  $(this).attr('id') + "']").clone();
			  var newId = vpArray[0] + ":" + vpArray[1] + ":" +  nextIndex;
			  alert(newId);
			  $(newRow).attr('id', newId);
			  newRow.find('button').attr('id', newId);
			  alert("" + $(newRow).html());
			  $("tr[id='" +  $(this).attr('id') + "']").after($(newRow));
			  
		});
	});
	**/
	 
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
	
});