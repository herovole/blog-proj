const path = require('path');
const webpack = require('webpack');

module.exports = {
    resolve: {extensions: ['.js', '.jsx', '.ts', '.tsx'],},
    entry: './src/main/js/index.tsx',
    output: {
        path: path.resolve(__dirname, './src/main/resources/static/dist'),
        filename: 'bundle.js',
    },
    devtool: "source-map",
    cache: true,
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.css$/,         // Target CSS files
                use: [
                    'style-loader',      // Injects CSS into the DOM
                    'css-loader'         // Interprets CSS as modules
                ]
            },
            {
                test: /\.tsx\?$/,        // Target TypeScript files
                use: [
                    'ts-loader'          // Compiles TypeScript to JavaScript
                ],
                exclude: /node_modules/ // Exclude node_modules directory
            },
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }
        ]
    },
    devServer: {
        static: {
            directory: path.join(__dirname, './src/main/resources/static/dist'), // Serve static files from "dist"
        },
        historyApiFallback: true, // Redirect all requests to index.html
        port: 8080, // Change the port if needed
        open: true, // Auto-open browser on start
        hot: true // Enable Hot Module Replacement (HMR)
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery': 'jquery',
        }),
    ]
};