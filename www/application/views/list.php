<?php
require_once ("header.php");

switch ($type) {
	case URL_EVENTS :
		$typeName = "Events";
		break;
	case URL_ADORATIONS :
		$typeName = "Adoration";
		break;
	case URL_MASSES :
		$typeName = "Masses";
		break;
	case URL_CONFESSIONS :
		$typeName = "Confessions";
		break;
	default :
		$typeName = "Unknown type of $type";
}

?>

<div class="container">

	<div class="row">
		<div id="items" class="col-md-10">
			<h2><?php echo $typeName;?> 
			 <?php if ($this->session->userdata(LOCATION)) {?>
			 	<small>  near <?php echo $this->session->userdata(LOCATION);?>
			 	</small>
			 <?php }?>
			</h2>
			<table class="table table-hover">
<?php
if ($items->num_rows () > 0) {
	foreach ( $items->result() as $item ) {
		echo "<tr><td>";
		if ($type == URL_EVENTS) {
			echo smallParishEventLink ( $item );
		} else {
			echo smallParishSacramentLink ( $item );
		}
		echo "</td></tr>";
	}
} else {
	echo "No upcoming $typeName in our database";
}
?>

			</table>
			<!-- /items -->
		</div>
		<!-- /row -->
	</div>
	<!-- /container -->
</div>
<?php
require_once ("footer.php");
?>