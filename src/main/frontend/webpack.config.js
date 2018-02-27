const path = require('path');
const merge = require('webpack-merge');

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    source: path.join(__dirname, 'app'),
    output: path.join(__dirname, '../../../target/classes/static')
};

const common = {
    entry: [
        PATHS.source
    ],
    output: {
        path: PATHS.output,
        publicPath: '',
        filename: 'bundle.js'
    },
    module: {
        loaders: [{
            exclude: /node_modules/,
            loader: 'babel'
        }, {
            test: /\.css$/,
            loader: 'style!css'
        }, {
            test: /\.gif/,
            loader: 'file-loader'
        }, {
            test: /\.ttf/,
            loader: 'file-loader'
        }, {
            test: /\.eot/,
            loader: 'file-loader'
        }, {
            test: /\.svg/,
            loader: 'file-loader'
        }, {
            test: /\.js$/,
            loaders: ['react-hot-loader/webpack', 'babel'],
            include: path.join(__dirname, 'src')
        }, {
            test: /\.less/,  loader: 'style!css!less?rootpath=/assets'
        }, {
            test: /\.woff/, loader: 'url-loader?limit=10000&minetype=application/font-woff'
        }, {
            test: /\.woff2/, loader: 'url-loader?limit=10000&minetype=application/font-woff'
        }]
    },
    resolve: {
        extensions: ['', '.js', '.jsx']
    }
};

if (TARGET === 'start' || !TARGET) {
    module.exports = merge(common, {
        devServer: {
            port: 9090,
            proxy: {
                '/': {
                    target: 'http://localhost:8080',
                    secure: false,
                    prependPath: false
                }
            },
            publicPath: 'http://localhost:9090/',
            historyApiFallback: true
        },
        devtool: 'source-map'
    });
}

if (TARGET === 'build') {
    module.exports = merge(common, {});
}

