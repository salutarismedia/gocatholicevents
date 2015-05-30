<?php require_once("header.php")?>

<font class=error> <?php echo validation_errors(); ?> </font>

<?php
$formTag = form_open ( 'auth' );
$formTag = str_replace ( "http", "https", $formTag );
echo $formTag;
?>
<h1>Parish Log In</h1>
<p class="lead">
	If you're a vistor looking for events, then no account is required! You
	can browse events for free from our <a
		href='<?php echo site_url('index')?>'>home page</a>.
</p>
<p class="h4">Parishes can sign in below to list their events.</p>
<table cellpadding=10>
	<tr>
		<td>
			<h5>Username</h5>
		</td>
		<td><input type="text" name="username"
			value="<?php echo set_value('username'); ?>" size="30" /></td>
	</tr>
	<tr>
		<td>
			<h5>Password</h5>
		</td>
		<td><input type="password" name="password" size="30" /></td>
	</tr>
	<tr>
		<td colspan=2 align=center><input type="submit" value="Submit" /></td>
	</tr>
	<tr>
		<td colspan=2 align=left><br> <b>Don't have a parish account?<br></b>  They're
			free!<a href='<?php echo site_url('contact')?>'>Contact us</a> and
			we'll create one for you!</td>
	</tr>
</table>

</form>


<?php require_once("footer.php")?>