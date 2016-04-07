const gulp = require('gulp');
const del = require('del');
const typescript = require('gulp-typescript');
const tscConfig = require('./tsconfig.json');
const sourcemaps = require('gulp-sourcemaps');
const tslint = require('gulp-tslint');
var browserSync = require('browser-sync').create();
var reload = browserSync.reload;

// clean the contents of the distribution directory
gulp.task('clean', function() {
	return del('dist/**/*');
});

gulp.task('tslint', function() {
	return gulp.src('app/**/*.ts').pipe(tslint())
			.pipe(tslint.report('verbose'));
});

// TypeScript compile
gulp.task('compile', [ 'clean' ], function() {
	return gulp
			.src(
					[ './app/**/*.ts',
							'./node_modules/angular2/typings/browser.d.ts' ])
			.pipe(sourcemaps.init()) // <--- sourcemaps
			.pipe(typescript(tscConfig.compilerOptions)).pipe(
					sourcemaps.write('.')) // <--- sourcemaps
			.pipe(gulp.dest('dist/app'));
});

gulp
		.task(
				'copy:libs:js',
				[ 'clean' ],
				function(done) {
					return gulp
							.src(
									[
											'node_modules/es6-shim/es6-shim.min.js',
											'node_modules/systemjs/dist/system-polyfills.js',
											'node_modules/angular2/es6/dev/src/testing/shims_for_IE.js',
											'node_modules/angular2/bundles/angular2-polyfills.js',
											'node_modules/systemjs/dist/system.src.js',
											'node_modules/rxjs/bundles/Rx.js',
											'node_modules/angular2/bundles/angular2.dev.js',
											'node_modules/angular2/bundles/router.dev.js' ])
							.pipe(gulp.dest('dist/lib/js'))
				});

gulp.task('copy:libs:js:agGrid', [ 'clean' ], function(done) {
	gulp.src([ 'node_modules/ag-grid/main.js' ]).pipe(
			gulp.dest('dist/lib/js/ag-grid'));

	gulp.src([ 'node_modules/ag-grid/dist/**/*.js' ]).pipe(
			gulp.dest('dist/lib/js/ag-grid/dist'));

	gulp.src([ 'node_modules/ag-grid-ng2/main.js' ]).pipe(
			gulp.dest('dist/lib/js/ag-grid-ng2'));

	return gulp.src([ 'node_modules/ag-grid-ng2/lib/agGridNg2.js' ]).pipe(
			gulp.dest('dist/lib/js/ag-grid-ng2/lib'))
});

gulp.task('copy:libs:css', [ 'clean' ], function() {
	return gulp.src(
			[
			// ag-grid
			'node_modules/ag-grid/dist/styles/ag-grid.css',
					'node_modules/ag-grid/dist/styles/theme-fresh.css',
					'node_modules/bootstrap/dist/css/bootstrap.min.css' ])
			.pipe(gulp.dest('dist/lib/css'))
});

gulp.task('copy:assets', [ 'clean' ], function() {
	return gulp.src([ 'app/**/*', 'index.html', 'styles.css', '!app/**/*.ts' ],
			{
				base : './'
			}).pipe(gulp.dest('dist'))
});

gulp.task('build', [ 'tslint', 'compile', 'copy:libs:css', 'copy:libs:js',
		'copy:libs:js:agGrid', 'copy:assets' ]);
gulp.task('default', [ 'build' ]);

// Watch out!
gulp.task('watch.assets', [ 'copy:assets' ], function() {
	return gulp.watch(
			[ 'app/**/*', 'index.html', 'styles.css', '!app/**/*.ts' ],
			[ 'copy:assets' ]);
});

gulp.task('watch.ts', [ 'compile' ], function() {
	return gulp.watch('./app/**/*.ts', [ 'compile' ]);
});

// Serve
gulp.task('serve',
		function() {

			// Serve files from the root of this project
			browserSync.init({
				server : {
					baseDir : "./dist"
				}
			});

			gulp.watch(
					[ 'app/**/*', 'index.html', 'styles.css', 'app/**/*.ts' ],
					[ 'build' ]).on("change",
							reload);
		});