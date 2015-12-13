
<div class="hidden">
				<form action="/logout" method="post" id="logout-form">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<button type="submit">Log out</button>
				</form>
				</div>

</body>
</html>