$(document).ready(
		function() {

			var active = 0;

			$('input').focus(function() {
				$(this).closest('td').removeClass("scoresheet-normal-cell scoresheet-top-lineuporder-cell");
				$(this).closest('td').addClass("scoresheet-highlighted-cell");
			});

			$('input').focusout(function() {
				$(this).closest('td').removeClass("scoresheet-highlighted-cell");
				rowId = $(this).closest('tr').attr('id');
				if (rowId == null) {
					return false;
				}
				rowIdArray = rowId.split(":");
				if (rowIdArray.length < 3) {
					return false;
				}
				
				lineupOrderIndex = Number(rowIdArray[2]);
				
				if (lineupOrderIndex == 0) {
					$(this).closest('td').addClass("scoresheet-top-lineuporder-cell");
				} else {
					$(this).closest('td').addClass("scoresheet-normal-cell");
				}
			});

			$('input.text-input').keyup(
					function(e) {
						/**
						if (e.which == 39)
							$(this).closest('td').next().find('input').focus();
						else if (e.which == 37)
							$(this).closest('td').prev().find('input').focus();
						else 
						 */ 
						if (e.which == 40)
							$(this).closest('tr').next().find(
									'td:eq(' + $(this).closest('td').index()
											+ ')').find('input').focus();
						else if (e.which == 38)
							$(this).closest('tr').prev().find(
									'td:eq(' + $(this).closest('td').index()
											+ ')').find('input').focus();
					});

		});