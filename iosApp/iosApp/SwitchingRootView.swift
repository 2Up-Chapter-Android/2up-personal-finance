//
//  SwitchingRootView.swift
//  iosApp
//
//  Created by Ethan Nguyen on 11/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct SwitchingRootView: View {
    
//    @ObservedObject
//    var viewModel: ApplicationViewModel
    
    private let userDefaultsPublisher = NotificationCenter.default.publisher(for: UserDefaults.didChangeNotification)
    
    private let appActivePublisher = NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)
    
    var body: some View {
//        Group {
//            if viewModel.useCompose {
                ZStack {
                    Color("NavBar_Background")
                        .ignoresSafeArea()
                    
                    ComposeController()
                }
//            } else {
//                MainView(viewModel: viewModel)
//            }
//        }
//        viewModel.attach(viewModel: viewModel)
//        .onAppear(perform: viewModel.onAppear)
//        .onReceive(userDefaultsPublisher) { _ in
//            viewModel.useCompose = SettingsBundleHelper.getUseComposeValue()
//        }
//        .onChange(of: viewModel.useCompose) { newValue in
//            SettingsBundleHelper.setUseComposeValue(newValue: newValue)
//        }
//        .onReceive(appActivePublisher) { _ in
//            viewModel.onAppear()
//        }
        
    }
}

//struct SwitchingRootView_Previews: PreviewProvider {
//    static var previews: some View {
//        EmptyView()
//    }
//}
