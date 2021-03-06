<?php require_once("header.php")?>

<div class="container">

	<div class="row">
<?php if ($this->session->userdata(LOCATION)) {?>	
	<div id="divLocation">
			<p class="text-muted lead">Displaying closest events to <?php echo $this->session->userdata(LOCATION);?></p>
		</div>
	<?php }?>
		<div id="events" class="col-md-10">
			<h2>Events</h2>
			<table class="table table-hover">
<?php
if ($events->num_rows() > 0) {
foreach ( $events->result() as $event ) {
    echo "<tr><td>" . smallParishEventLink($event) . "</td></tr>";
}
}
else {
echo "No upcoming events currently scheduled";	
	}
?>
                <tr>
					<td><h5>
							<a href="<?php echo site_url('index/view/' . URL_EVENTS)?>">More
								Events <span class="glyphicon glyphicon-circle-arrow-right"></span>
							</a>
						</h5></td>
				</tr>
			</table>

		</div>


		<div class="col-md-4">
			<h2>Mass</h2>
			<table class="table table-hover">
<?php
foreach ( $masses->result() as $mass ) {
    echo "<tr><td>" . smallParishSacramentLink($mass) . "</td></tr>";
}
?>
            <tr>
					<td><h5>
							<a href="<?php echo site_url('index/view/' . URL_MASSES)?>">More
								Masses <span class="glyphicon glyphicon-circle-arrow-right"></span>
							</a>
						</h5></td>
				</tr>
			</table>

		</div>


		<div class="col-md-4">
			<h2>Adoration</h2>
			<table class="table table-hover">
<?php
foreach ( $adorations->result() as $adoration ) {
    echo "<tr><td>" . smallParishSacramentLink($adoration) . "</td></tr>";
}
?>          <tr>
					<td><h5>
							<a href="<?php echo site_url('index/view/' . URL_ADORATIONS)?>">More
								Adorations <span class="glyphicon glyphicon-circle-arrow-right"></span>
							</a>
						</h5></td>
				</tr>
			</table>
		</div>


		<div class="col-md-4">
			<h2>Confession</h2>
			<table class="table table-hover">
<?php
foreach ( $confessions->result() as $confession ) {
    echo "<tr><td>" . smallParishSacramentLink($confession) . "</td></tr>";
}
?>
            <tr>
					<td><h5>
							<a href="<?php echo site_url('index/view/' . URL_CONFESSIONS)?>">More
								Confessions <span class="glyphicon glyphicon-circle-arrow-right"></span>
							</a>
						</h5></td>
				</tr>
			</table>
		</div>
		<!-- /row -->
	</div>
	<!-- /container -->
</div>
<?php require_once("footer.php");?>