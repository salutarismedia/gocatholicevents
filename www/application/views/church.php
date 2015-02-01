<?php require_once("header.php")?>

<div class="container">
	<h1><?php echo $church->name;?> 
    <small><?php echo $churchAddress?></small>
	</h1>
	<a href="<?php echo $church->url?>" target="_blank"><?php echo $church->url?></a>
	- <small><a
		href="https://maps.google.com/maps?geocode=&q=<?php echo $church->name . ", " . $churchAddress?>"
		target="_blank">Get Directions</a> <span
		class="glyphicon glyphicon-new-window"></span> <?php echo $church->phone;?>
	</small>

	<div id="rowEvents" class="row">
		<div id="divEvents" class="col-lg-8">
			<h2>Latest Events</h2>
			<table class="table table-hover">
                <?php
                if ($events->num_rows() > 0) {
	                foreach ( $events->result() as $event ) {
	                    echo "<tr><td>" . largeEventLink($event) . "</td></tr>";
	                }
	            }
	            else {
					echo "<tr><td><h3>There are no upcoming events.  To see more visit <a href='$church->url'>$church->url</a></h3></td></tr>";
				}
                ?>
		</table>
			<!-- /divEvents -->
		</div>
		<!-- /rowEvents -->
	</div>
	<div id="rowStandard" class="row">
		<div class="col-md-4">
			<h2>Mass</h2>
			<table class="table table-hover">
                    <?php
                    foreach ( $masses->result() as $mass ) {
                        echo "<tr><td>" . smallSacramentLink($mass) . "</tr></td>";
                    }
                    ?>
		          </table>
			<!-- /mass -->
		</div>

		<div class="col-md-4">
			<h2>Confession</h2>
			<table class="table table-hover">
                    <?php
                    foreach ( $confessions->result() as $confession ) {
                        echo "<tr><td>" . smallSacramentLink($confession) . "</tr></td>";
                    }
                    ?>
		          </table>
			<!-- /confession -->
		</div>

		<div class="col-md-4">
			<h2>Adoration</h2>
			<table class="table table-hover">
                    <?php
                    foreach ( $adorations->result() as $adoration ) {
                        echo "<tr><td>" . smallSacramentLink($adoration) . "</tr></td>";
                    }
                    ?>
		          </table>
			<!-- /Adoration -->
		</div>
		<!-- /rowStandard -->
	</div>
	<!-- /container -->
</div>
<?php require_once("footer.php")?>