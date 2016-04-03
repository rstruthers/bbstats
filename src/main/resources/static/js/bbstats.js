$(document).ready(function(){
	
	if ($('#season').length) {
		populateTeamDropdowns();
	}

	$("#logout").click(function(){
		$( "#signout-form" ).submit();
	});
	
	$('#season').change(populateTeamDropdowns);
	 
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