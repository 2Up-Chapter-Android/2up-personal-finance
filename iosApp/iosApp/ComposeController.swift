//
//  ComposeController.swift
//  iosApp
//
//  Created by Ethan Nguyen on 11/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct ComposeController: UIViewControllerRepresentable {
    
    func makeUIViewController(context: Context) -> some UIViewController {
        let controller = BackgroundCrashWorkaroundController()
        controller.view.backgroundColor = UIColor.clear
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        uiViewController.view.setNeedsLayout()
    }
}
