<?php require_once("parishaccount-header.php")?>

<div class="container">
	<h1>Welcome <?php echo $user->name;?> </h1>
	<h5>You are currently registered to:</h5>
	<address>
		<strong><?php echo $church->name?></strong><br>
  				<?php echo $church->streetAddress?><br>
  				<?php echo $church->city?>, <?php echo $church->state?> <?php echo $church->zip?><br>
		<abbr title="Phone">P:</abbr>  <?php echo $church->phone?><br> <abbr
			title="Homepage">W:</abbr> <a href="<?php echo $church->url?>"
			target="_blank"><?php echo $church->url?></a><br> <abbr
			title="Coordinates">C:</abbr>  <?php echo $church->lat?>,<?php echo $church->lon?></abbr><br>
	</address>

	<div id="rowEvents" class="row">
		<div id="divEvents" class="col-md-8">
			<h2>Parish Events</h2>
			<div class="row" ng-repeat="event in events">
				<div class="col-md-2">{{event.name}}</div>
			</div>
			<!-- /divEvents -->
		</div>
		<!-- /rowEvents -->
	</div>
	<h1>Parish Sacraments</h1>
	<p class="lead">Please list when sacraments are available below</p>

	<div class="col-md-4">
		<h2>Mass</h2>
		<table class="table table-hover">TODO
		</table>
		<!-- /mass -->
	</div>

	<div class="col-md-4">
		<h2>Confession</h2>
		<table class="table table-hover">TODO
		</table>
		<!-- /confession -->
	</div>

	<div class="col-md-4">
		<h2>Adoration</h2>
		<table class="table table-hover">TODO
		</table>
		<!-- /Adoration -->
	</div>
	<!-- /rowStandard -->
</div>
<!-- /container -->

</div>
<?php require_once("footer.php")?>